package mosis.streetsandtotems.feature_auth.data.repository

import android.content.Intent
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirebaseAuthConstants
import mosis.streetsandtotems.core.FirebaseErrorCodesConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.data.data_source.OneTapGoogleDataSource
import mosis.streetsandtotems.feature_auth.domain.model.SignInError
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val oneTapGoogleDataSource: OneTapGoogleDataSource
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
                emit(
                    Response.Error(
                        message = MessageConstants.DEFAULT_ERROR_MESSAGE
                    )
                )
            }
        }

    override suspend fun emailAndPasswordSignUp(
        email: String,
        password: String
    ): Flow<Response<Nothing>> =
        flow {
            try {
                emit(Response.Loading)
                authDataSource.emailAndPasswordSignUp(email, password).await()
                emit(Response.Success())
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
            }
        }

    override suspend fun signOut(): Flow<Response<Nothing>> = flow {
        try {
            authDataSource.signOut()
        } catch (e: Exception) {
            emit(
                Response.Error(
                    message = e.message ?: e.toString()
                )
            )
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
                // emit(Response.Error())
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

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(Response.Loading)
            val authResult = authDataSource.signInWithCredential(googleCredential).await()
//            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
//            if (isNewUser) {
//                addUserToFirestore()
//            }
            emit(Response.Success())
        } catch (e: Exception) {
            emit(Response.Error())
        }
    }

    override fun getSignInCredentialFromIntent(intent: Intent): SignInCredential {
        return oneTapGoogleDataSource.getSignInCredentialFromIntent(intent)
    }
}