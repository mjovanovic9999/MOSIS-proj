package mosis.streetsandtotems.services

import mosis.streetsandtotems.feature_map.domain.model.ProfileData

sealed class LocationServiceMainScreenEvents {
    data class CurrentUserProfileDataChanged(val newUserData: ProfileData) :
        LocationServiceMainScreenEvents()
}