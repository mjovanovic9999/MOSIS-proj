package mosis.streetsandtotems.core.presentation.screens.tiki

sealed class TikiScreenViewModelEvents {
    object SendVerificationEmail : TikiScreenViewModelEvents()
    object GoToSignInScreen : TikiScreenViewModelEvents()
    object SendRecoveryEmail : TikiScreenViewModelEvents()
}
