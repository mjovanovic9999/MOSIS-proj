package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint


interface MapRepository {
    /*
    updateMyLocation,
    placeCustomPin,

    */

    suspend fun updateMyLocation(newLocation: GeoPoint)
}