package mosis.streetsandtotems.feature_auth.presentation.profile

import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFields

sealed class ProfileViewModelEvents {
    object ChangeMode : ProfileViewModelEvents()
    object ShowEditPasswordDialog : ProfileViewModelEvents()
    object HideEditPasswordDialog : ProfileViewModelEvents()
    object UpdatePassword : ProfileViewModelEvents()
    data class InitializeFormFields(val currentUserFields: ProfileFields) : ProfileViewModelEvents()
    object ChangeProfileData : ProfileViewModelEvents()
}