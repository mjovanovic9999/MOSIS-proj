package mosis.streetsandtotems.feature_auth.domain.use_case

import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class SignInWithGoogle(private val repository: AuthRepository) {
    suspend operator fun invoke(intent: Intent): Flow<Response<Nothing>> {
        return try {
            val credentials = repository.getSignInCredentialFromIntent(intent)
            val googleIdToken = credentials.googleIdToken
            val googleCredentials = getCredential(googleIdToken, null)
            repository.firebaseSignInWithGoogle(googleCredentials)
        } catch (it: ApiException) {
            emptyFlow<Nothing>()
        }
    }
}