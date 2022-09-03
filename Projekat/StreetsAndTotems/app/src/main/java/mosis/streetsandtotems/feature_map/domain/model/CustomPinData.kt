package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

data class CustomPinData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val visible_to: String? = null,
    val placed_by: String? = null,
    val text: String? = null,
) : IData
