package mosis.streetsandtotems.services

import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

sealed class LocationServiceInventoryEvents {
    data class UserInventoryChanged(val newInventory: UserInventoryData) :
        LocationServiceInventoryEvents()
}