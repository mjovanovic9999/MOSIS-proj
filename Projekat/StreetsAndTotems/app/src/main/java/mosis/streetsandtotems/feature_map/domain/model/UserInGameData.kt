package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

data class UserInGameData(
    val id: String? = null,
    val l: GeoPoint? = null,
    val calls_allowed: Boolean? = null,
    val messaging_allowed: Boolean? = null,
    val profile_data: DocumentReference? = null
)
