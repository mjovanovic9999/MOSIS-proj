package mosis.streetsandtotems.feature_auth.data.repository

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.FirebaseAuthConstants
import mosis.streetsandtotems.core.FirebaseErrorCodesConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.data.data_source.UserOnlineStatusDataSource
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseStorageDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.FirestoreAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.OneTapGoogleDataSource
import mosis.streetsandtotems.feature_auth.domain.model.SignInError
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFields
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val oneTapGoogleDataSource: OneTapGoogleDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val firestoreAuthDataSource: FirestoreAuthDataSource,
    private val userOnlineStatusDataSource: UserOnlineStatusDataSource,
    private val firebaseStorageDataSource: FirebaseStorageDataSource
) : AuthRepository {
    override fun isUserAuthenticated(): Boolean = authDataSource.getCurrentUser() != null

    override suspend fun emailAndPasswordSignIn(
        email: String, password: String
    ): Flow<Response<SignInError>> = flow {
        try {
            emit(Response.Loading)
            if (isUserAlreadyLoggedIn(email)) emit(Response.Error(message = MessageConstants.ALREADY_LOGGED_IN))
            else {
                authDataSource.emailAndPasswordSignIn(email, password).await()
                preferencesDataStore.saveAuthProvider(AuthProvider.EmailAndPassword)
                preferencesDataStore.setUserId(authDataSource.getCurrentUser()!!.uid)
                if (authDataSource.getCurrentUser()!!.isEmailVerified) emit(Response.Success())
                else emit(
                    Response.Error(
                        message = MessageConstants.EMAIL_NOT_VERIFIED, data = SignInError(
                            wrongEmail = false, wrongPassword = false, emailNotVerified = true
                        )
                    )
                )
            }
        } catch (e: FirebaseAuthException) {
            emit(
                Response.Error(
                    message = FirebaseAuthConstants.AUTH_ERRORS[e.errorCode], data = SignInError(
                        wrongEmail = e.errorCode == FirebaseErrorCodesConstants.USER_NOT_FOUND,
                        wrongPassword = e.errorCode == FirebaseErrorCodesConstants.WRONG_PASSWORD,
                        emailNotVerified = false
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
            if (isUserAuthenticated()) signOut(emitError = false)
            emit(
                Response.Error(
                    message = MessageConstants.DEFAULT_ERROR_MESSAGE
                )
            )
        }
    }

    override suspend fun emailAndPasswordSignUp(
        password: String, profileData: SignUpFields
    ): Flow<Response<Nothing>> = flow {
        try {
            emit(Response.Loading)
            if (!firestoreAuthDataSource.getUsersWithUsername(profileData.userName)
                    .await().isEmpty
            ) emit(Response.Error(message = MessageConstants.USERNAME_TAKEN))
            else {
                authDataSource.emailAndPasswordSignUp(profileData.email, password).await()
                val userSettings = preferencesDataStore.getUserSettings()
                val currentUser = authDataSource.getCurrentUser()!!
                firebaseStorageDataSource.storeProfileImage(
                    currentUser.uid, File(profileData.imagePath).readBytes()
                ).await()
                val imageUrl = firebaseStorageDataSource.getDownloadUrl(currentUser.uid).await()
                firestoreAuthDataSource.addUser(
                    ProfileData(
                        id = currentUser.uid,
                        l = GeoPoint(0.0, 0.0),
                        user_name = profileData.userName,
                        first_name = profileData.firstName,
                        last_name = profileData.lastName,
                        phone_number = profileData.phoneNumber,
                        squad_id = "",
                        email = profileData.email,
                        image_uri = imageUrl.toString(),
                        call_privacy_level = userSettings.callPrivacyLevel,
                        messaging_privacy_level = userSettings.smsPrivacyLevel,
                        is_online = false
                    )
                )
                currentUser.sendEmailVerification().await()
                emit(Response.Success())
            }
        } catch (e: FirebaseAuthException) {
            emit(
                Response.Error(
                    message = FirebaseAuthConstants.AUTH_ERRORS[e.errorCode],
                )
            )
        } catch (e: Exception) {
            emit(
                Response.Error(
                    message = e.message ?: e.toString(),
                )
            )
            try {
                if (isUserAuthenticated()) {
                    val currentUserId = authDataSource.getCurrentUser()!!.uid
                    authDataSource.removeCurrentUser()?.await()
                    firestoreAuthDataSource.removeUser(currentUserId)
                    firebaseStorageDataSource.getDownloadUrl(currentUserId).addOnSuccessListener {
                        CoroutineScope(Dispatchers.Default).launch {
                            firebaseStorageDataSource.removeProfileImage(currentUserId).await()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("tag", "Rollback failed")
            }
        }
    }

    override suspend fun signOut(emitError: Boolean) = flow {
        try {
            emit(Response.Loading)
            userOnlineStatusDataSource.updateUserOnlineStatus(
                false, authDataSource.getCurrentUser()!!.uid
            )
            if (preferencesDataStore.getAuthProvider() == AuthProvider.Google) oneTapGoogleDataSource.signOut()
            authDataSource.signOut()
            preferencesDataStore.setUserId("")
            preferencesDataStore.setSquadId("")
            emit(Response.Success())
        } catch (e: Exception) {
            if (emitError) emit(Response.Error())
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
            if (isUserAlreadyLoggedIn(credentials.id)) emit(Response.Error(message = MessageConstants.ALREADY_LOGGED_IN))
            else {
                val authResult = authDataSource.signInWithCredential(googleCredentials).await()
                preferencesDataStore.saveAuthProvider(AuthProvider.Google)
                preferencesDataStore.setUserId(authDataSource.getCurrentUser()!!.uid)

                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                if (isNewUser) firestoreAuthDataSource.addUser(getProfileDataFromGoogle(credentials))
                    ?.await()
                emit(Response.Success())
            }
        } catch (e: Exception) {
            if (isUserAuthenticated()) signOut(emitError = false)
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
            emit(Response.Error(e.message))
        }
    }

    override suspend fun sendRecoveryEmail(email: String): Flow<Response<Nothing>> = flow {
        try {
            emit(Response.Loading)
            if (firestoreAuthDataSource.getUsersWithEmail(email)
                    .await().isEmpty
            ) emit(Response.Error(MessageConstants.NO_USER_ACCOUNT_WITH_THIS_EMAIL))
            else {
                authDataSource.sendRecoveryEmail(email).await()
                emit(Response.Success())
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message))
        }
    }

    private suspend fun isUserAlreadyLoggedIn(email: String): Boolean {
        return firestoreAuthDataSource.getUsersWithEmail(email).await().documents.firstOrNull()
            ?.getBoolean(FireStoreConstants.IS_ONLINE_FIELD) == true
    }

    override suspend fun isCurrentUserAlreadyLoggedIn(): Boolean {
        val currentUser = authDataSource.getCurrentUser()
        return if (currentUser != null && currentUser.email != null) isUserAlreadyLoggedIn(
            currentUser.email!!
        ) else false
    }

    override suspend fun isCurrentUserEmailValidated(): Boolean {
        return authDataSource.getCurrentUser()?.isEmailVerified ?: false
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