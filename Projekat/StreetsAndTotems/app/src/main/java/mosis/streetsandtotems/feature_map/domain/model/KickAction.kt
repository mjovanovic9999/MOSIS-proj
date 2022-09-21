package mosis.streetsandtotems.feature_map.domain.model

enum class KickActionType {
    VoteStarted,
    VoteEnded
}

data class KickAction(val type: KickActionType, val kickVoteData: KickVoteData)