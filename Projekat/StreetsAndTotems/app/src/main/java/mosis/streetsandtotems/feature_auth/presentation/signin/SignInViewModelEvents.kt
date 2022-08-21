package mosis.streetsandtotems.feature_auth.presentation.signin

sealed class SignInViewModelEvents {
    object SignInWithEmailAndPassword : SignInViewModelEvents()
}