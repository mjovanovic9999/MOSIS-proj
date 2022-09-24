package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.ProtectionLevelConstants

data class TotemData(
    @get:Exclude
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val placed_by: String? = null, //"auth_id" || SQUAD,
    val placing_time: Timestamp? = null,
    val last_visited: Timestamp? = null,
    val bonus_points: Int? = null,
    val protection_points: Int? = 0,
    val visible_to: String? = null, //"" ili squad
) : Data


sealed class ProtectionLevel {
    object Unprotected : ProtectionLevel()
    sealed class RiddleProtectionLevel() : ProtectionLevel() {
        object Low : RiddleProtectionLevel()
        object Medium : RiddleProtectionLevel()
        object High : RiddleProtectionLevel()
    }
}