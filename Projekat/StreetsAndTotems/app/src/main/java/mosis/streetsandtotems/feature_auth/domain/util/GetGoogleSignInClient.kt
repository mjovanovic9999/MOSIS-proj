package mosis.streetsandtotems.feature_auth.domain.util

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import mosis.streetsandtotems.R

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(R.string.default_web_client_id.toString())
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)

}