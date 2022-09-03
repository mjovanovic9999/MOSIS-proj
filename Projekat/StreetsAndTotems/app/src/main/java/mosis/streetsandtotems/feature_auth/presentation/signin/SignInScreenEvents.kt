package mosis.streetsandtotems.feature_auth.presentation.signin

sealed class SignInScreenEvents {
    object SignInSuccessful : SignInScreenEvents()
    object WrongEmail : SignInScreenEvents()
    object WrongPassword : SignInScreenEvents()
}
