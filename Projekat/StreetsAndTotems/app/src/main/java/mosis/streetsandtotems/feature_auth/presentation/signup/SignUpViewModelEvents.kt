package mosis.streetsandtotems.feature_auth.presentation.signup

import android.content.Intent

sealed class SignUpViewModelEvents {
    object SignUpWithEmailAndPassword : SignUpViewModelEvents()
    data class SignUpWithGoogle(val intent: Intent?) : SignUpViewModelEvents()
    object OneTapGoogleSignUp : SignUpViewModelEvents()
}
