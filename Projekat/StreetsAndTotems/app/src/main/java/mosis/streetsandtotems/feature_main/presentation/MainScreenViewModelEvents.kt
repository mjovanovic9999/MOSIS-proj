package mosis.streetsandtotems.feature_main.presentation

sealed class MainScreenViewModelEvents {
    object SignOut : MainScreenViewModelEvents()
    object OnResume : MainScreenViewModelEvents()
    object OnPause : MainScreenViewModelEvents()
}