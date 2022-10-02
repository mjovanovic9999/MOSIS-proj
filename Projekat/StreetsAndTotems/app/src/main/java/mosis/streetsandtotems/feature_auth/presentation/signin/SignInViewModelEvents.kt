package mosis.streetsandtotems.feature_auth.presentation.signin

import android.content.Intent

sealed class SignInViewModelEvents {
    object SignInWithEmailAndPassword : SignInViewModelEvents()
    object OneTapSignInWithGoogle : SignInViewModelEvents()
    data class SignInWithGoogle(val intent: Intent?) : SignInViewModelEvents()
}