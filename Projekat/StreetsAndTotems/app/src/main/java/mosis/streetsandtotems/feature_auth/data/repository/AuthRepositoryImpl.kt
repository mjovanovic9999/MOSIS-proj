package mosis.streetsandtotems.feature_auth.data.repository

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirebaseAuthConstants
import mosis.streetsandtotems.core.FirebaseErrorCodesConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirestoreAuthDataSource
import mosis.streetsandtotems.core.data.data_source.UserOnlineStatusDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.OneTapGoogleDataSource
import mosis.streetsandtotems.feature_auth.domain.model.SignInError
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFields
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val oneTapGoogleDataSource: OneTapGoogleDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val firestoreAuthDataSource: FirestoreAuthDataSource,
    private val userOnlineStatusDataSource: UserOnlineStatusDataSource
) :
    AuthRepository {
    override fun isUserAuthenticated(): Boolean = authDataSource.getCurrentUser() != null

    override suspend fun emailAndPasswordSignIn(
        email: String,
        password: String
    ): Flow<Response<SignInError>> =
        flow {
            try {
                emit(Response.Loading)
                authDataSource.emailAndPasswordSignIn(email, password).await()
                preferencesDataStore.saveAuthProvider(AuthProvider.EmailAndPassword)
                preferencesDataStore.setUserId(authDataSource.getCurrentUser()!!.uid)
                emit(Response.Success())
            } catch (e: FirebaseAuthException) {
                emit(
                    Response.Error(
                        message = FirebaseAuthConstants.AUTH_ERRORS[e.errorCode],
                        data = SignInError(
                            wrongEmail = e.errorCode == FirebaseErrorCodesConstants.USER_NOT_FOUND,
                            wrongPassword = e.errorCode == FirebaseErrorCodesConstants.WRONG_PASSWORD
                        )
                    )
                )
            } catch (e: FirebaseTooManyRequestsException) {
                emit(
                    Response.Error(
                        message = MessageConstants.TOO_MANY_LOGIN_ATTEMPTS
                    )
                )
            } catch (e: Exception) {
                Log.d("tag", e.toString())
                if (isUserAuthenticated())
                    signOut(emitError = false)
                emit(
                    Response.Error(
                        message = MessageConstants.DEFAULT_ERROR_MESSAGE
                    )
                )
            }
        }

    override suspend fun emailAndPasswordSignUp(
        password: String,
        profileData: SignUpFields
    ): Flow<Response<Nothing>> =
        flow {
            try {
                emit(Response.Loading)
                authDataSource.emailAndPasswordSignUp(profileData.email, password).await()
                val userSettings = preferencesDataStore.getUserSettings()
                firestoreAuthDataSource.addUser(
                    ProfileData(
                        id = authDataSource.getCurrentUser()!!.uid,
                        l = GeoPoint(0.0, 0.0),
                        user_name = "",
                        first_name = profileData.firstName,
                        last_name = profileData.lastName,
                        phone_number = profileData.phoneNumber,
                        squad_id = profileData.userName,
                        email = profileData.email,
                        image_uri = profileData.imageUri,
                        call_privacy_level = userSettings.callPrivacyLevel,
                        messaging_privacy_level = userSettings.smsPrivacyLevel,
                        is_online = true
                    )
                )
                emit(Response.Success())
            } catch (e: FirebaseAuthException) {
                emit(
                    Response.Error(
                        message = FirebaseAuthConstants.AUTH_ERRORS[e.errorCode],
                    )
                )
            } catch (e: Exception) {
                if (isUserAuthenticated())
                    signOut(false)
                emit(
                    Response.Error(
                        message = e.message ?: e.toString(),
                    )
                )
            }
        }

    override suspend fun signOut(emitError: Boolean) = flow {
        try {
            emit(Response.Loading)
            userOnlineStatusDataSource.updateUserOnlineStatus(
                false,
                authDataSource.getCurrentUser()!!.uid
            )
            if (preferencesDataStore.getAuthProvider() == AuthProvider.Google)
                oneTapGoogleDataSource.signOut()
            authDataSource.signOut()
            preferencesDataStore.setUserId("")
            emit(Response.Success())
        } catch (e: Exception) {
            if (emitError)
                emit(Response.Error())
        }
    }

    override suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Response.Loading)
            val result = oneTapGoogleDataSource.oneTapSignIn().await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapGoogleDataSource.oneTapSignUp().await()
                emit(Response.Success(signUpResult))
            } catch (e: Exception) {
                emit(Response.Error())
            }
        }
    }

    override suspend fun oneTapSignUpWithGoogle() = flow {
        try {
            emit(Response.Loading)
            val result = oneTapGoogleDataSource.oneTapSignUp().await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error())
        }
    }

    override suspend fun firebaseSignInWithGoogle(credentials: SignInCredential) = flow {
        try {
            emit(Response.Loading)
            val googleIdToken = credentials.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = authDataSource.signInWithCredential(googleCredentials).await()
            preferencesDataStore.saveAuthProvider(AuthProvider.Google)
            preferencesDataStore.setUserId(authDataSource.getCurrentUser()!!.uid)

            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) firestoreAuthDataSource.addUser(getProfileDataFromGoogle(credentials))
            emit(Response.Success())
        } catch (e: Exception) {
            if (isUserAuthenticated())
                signOut(emitError = false)
            Log.d("tag", e.message.toString())
            emit(Response.Error())
        }
    }

    override fun getSignInCredentialFromIntent(intent: Intent): SignInCredential {
        return oneTapGoogleDataSource.getSignInCredentialFromIntent(intent)
    }

    override suspend fun sendValidationEmail(): Flow<Response<Nothing>> = flow {
        try {
            emit(Response.Loading)
            val user = authDataSource.getCurrentUser()
            user?.sendEmailVerification()?.await()
            emit(Response.Success())
        } catch (e: Exception) {
            emit(Response.Error())
        }
    }

    private suspend fun getProfileDataFromGoogle(credentials: SignInCredential): ProfileData {
        val userSettings = preferencesDataStore.getUserSettings()
        return ProfileData(
            id = authDataSource.getCurrentUser()!!.uid,
            l = GeoPoint(0.0, 0.0),
            user_name = credentials.displayName,
            first_name = credentials.givenName,
            last_name = credentials.familyName,
            phone_number = credentials.phoneNumber,
            squad_id = "",
            email = credentials.id,
            image_uri = credentials.profilePictureUri.toString(),
            call_privacy_level = userSettings.callPrivacyLevel,
            messaging_privacy_level = userSettings.smsPrivacyLevel,
            is_online = true
        )
    }
}