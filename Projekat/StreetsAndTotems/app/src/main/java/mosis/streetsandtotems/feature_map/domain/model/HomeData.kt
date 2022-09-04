package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

data class HomeData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val inventory: InventoryData? = null,
) : Data
