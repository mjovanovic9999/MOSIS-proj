package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

enum class ResourceType {
    Wood,
    Brick,
    Stone,
    Emerald
}

data class ResourceData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val type: ResourceType? = null,
    val remaining: Int? = null
) : Data

