package mosis.streetsandtotems.feature_leaderboards.presentation

import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import mosis.streetsandtotems.feature_map.domain.model.ProfileData

data class LeaderboardsState(
    val leaderboardUsers: List<LeaderboardUserData>,
    val playerDialogOpen: Boolean,
    val playerDialogData: ProfileData?,
    val mySquadId: String,
    val myId: String,
)