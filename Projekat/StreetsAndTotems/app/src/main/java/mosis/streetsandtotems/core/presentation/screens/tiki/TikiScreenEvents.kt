package mosis.streetsandtotems.core.presentation.screens.tiki

sealed class TikiScreenEvents {
    object GoToSignInScreen : TikiScreenEvents()
    object ClearFocus : TikiScreenEvents()
}