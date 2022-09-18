package mosis.streetsandtotems.feature_map.domain.model

data class KickVoteData(
    val squad_id: String? = null,
    val user_id: String? = null,
    val votes: Map<String, Vote>? = null,
)

enum class Vote {
    Unanswered,
    No,
    Yes,
}