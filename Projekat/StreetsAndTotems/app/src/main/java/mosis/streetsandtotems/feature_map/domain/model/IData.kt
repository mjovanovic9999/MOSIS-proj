package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

interface IData {
    val id: String?
    val l: GeoPoint?
}