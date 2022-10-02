package mosis.streetsandtotems.feature_auth.data.data_source

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import mosis.streetsandtotems.core.DINameConstants
import javax.inject.Inject
import javax.inject.Named

class OneTapGoogleDataSource @Inject constructor(
    private var oneTapClient: SignInClient,
    @Named(DINameConstants.SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(DINameConstants.SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest
) {
    fun oneTapSignIn(): Task<BeginSignInResult> {
        return oneTapClient.beginSignIn(signInRequest)
    }

    fun oneTapSignUp(): Task<BeginSignInResult> {
        return oneTapClient.beginSignIn(signUpRequest)
    }

    fun getSignInCredentialFromIntent(intent: Intent): SignInCredential =
        oneTapClient.getSignInCredentialFromIntent(intent)

    fun signOut(): Task<Void> = oneTapClient.signOut()

}