package mosis.streetsandtotems.services

import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

sealed class LocationServiceCommonEvents {
    data class UserInventoryChanged(val newInventory: UserInventoryData) :
        LocationServiceCommonEvents()
}