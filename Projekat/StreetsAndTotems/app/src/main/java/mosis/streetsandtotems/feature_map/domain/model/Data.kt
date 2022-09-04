package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

interface Data {
    val id: String?
    val l: GeoPoint?
}