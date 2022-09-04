package mosis.streetsandtotems.feature_auth.domain.repository

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.domain.model.SignInError

interface AuthRepository {
    fun isUserAuthenticated(): Boolean

    suspend fun emailAndPasswordSignIn(email: String, password: String): Flow<Response<SignInError>>

    suspend fun emailAndPasswordSignUp(email: String, password: String): Flow<Response<Nothing>>

    suspend fun signOut(): Flow<Response<Nothing>>

    suspend fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun oneTapSignUpWithGoogle(): Flow<Response<BeginSignInResult>>

    suspend fun firebaseSignInWithGoogle(credentials: SignInCredential): Flow<Response<Nothing>>

    fun getSignInCredentialFromIntent(intent: Intent): SignInCredential

}