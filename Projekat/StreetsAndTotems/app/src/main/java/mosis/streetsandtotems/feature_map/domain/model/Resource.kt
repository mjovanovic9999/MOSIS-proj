package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

enum class ResourceType {
    Wood,
    Brick,
    Stone,
    Emerald
}

data class Resource(
    val id: String? = null,
    val type: ResourceType? = null,
    val l: GeoPoint? = null,
    val remaining: Int? = null
)
