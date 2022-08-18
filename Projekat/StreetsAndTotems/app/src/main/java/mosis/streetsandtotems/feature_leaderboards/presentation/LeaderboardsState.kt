package mosis.streetsandtotems.feature_leaderboards.presentation

import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUser

data class LeaderboardsState(
    val leaderboardUsers: List<LeaderboardUser>,
    val playerDialogOpen: Boolean
)