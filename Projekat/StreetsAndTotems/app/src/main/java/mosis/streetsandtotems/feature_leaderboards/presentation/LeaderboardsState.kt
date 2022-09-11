package mosis.streetsandtotems.feature_leaderboards.presentation

import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData

data class LeaderboardsState(
    val leaderboardUsers: List<LeaderboardUserData>,
    val playerDialogOpen: Boolean
)