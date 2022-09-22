package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.Exclude

data class KickVoteData(
    @get:Exclude
    val id: String? = null,
    val squad_id: String? = null,
    val user_id: String? = null,
    val votes: Map<String, Vote>? = null,
)

enum class Vote {
    Unanswered,
    No,
    Yes,
}