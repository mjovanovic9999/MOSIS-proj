package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

data class MarketData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val items: Map<String, MarketItem>? = null,
) : Data

data class MarketItem(
    val currency_type: ResourceType? = null,
    val amount_left: Int? = null,
    val price: Int? = null,
)