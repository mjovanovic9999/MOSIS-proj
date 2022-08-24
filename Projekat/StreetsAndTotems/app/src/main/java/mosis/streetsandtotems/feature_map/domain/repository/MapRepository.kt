package mosis.streetsandtotems.feature_map.domain.repository

import mosis.streetsandtotems.feature_map.domain.model.Location

interface MapRepository {
    /*
    updateMyLocation,
    placeCustomPin,

    */

    suspend fun updateMyLocation(newLocation: Location)
}