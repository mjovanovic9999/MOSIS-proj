package mosis.streetsandtotems.feature_main.presentation

import mosis.streetsandtotems.core.domain.model.PrivacySettings

sealed class MainScreenViewModelEvents {
    object LeaveSquad : MainScreenViewModelEvents()
    object SignOut : MainScreenViewModelEvents()
    object OnResume : MainScreenViewModelEvents()
    object OnPause : MainScreenViewModelEvents()
    object ToggleRunInBackground : MainScreenViewModelEvents()
    object ToggleNotifications : MainScreenViewModelEvents()

    data class ChangeCallPrivacyLevel(val privacyLevel: PrivacySettings) :
        MainScreenViewModelEvents()

    data class ChangeSmsPrivacyLevel(val privacyLevel: PrivacySettings) :
        MainScreenViewModelEvents()
}