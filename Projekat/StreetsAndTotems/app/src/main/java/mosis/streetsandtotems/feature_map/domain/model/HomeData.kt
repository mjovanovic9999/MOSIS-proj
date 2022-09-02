package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

data class HomeData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val emerald: Int? = null,
    val stone: Int? = null,
    val brick: Int? = null,
    val wood: Int? = null,
    val totem: Int? = null,
) : IData
