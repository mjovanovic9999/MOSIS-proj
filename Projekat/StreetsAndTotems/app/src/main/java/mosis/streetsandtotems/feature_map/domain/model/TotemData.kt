package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class TotemData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val placed_by: String? = null, //"auth_id",
    val placing_time: Timestamp? = null,
    val last_visited: Timestamp? = null,
    val bonus_points: Int? = null,
    val protection_level: ProtectionLevel? = null,
) : Data

enum class ProtectionLevel {
    Unprotected,
    Low,
    Medium,
    High,
}