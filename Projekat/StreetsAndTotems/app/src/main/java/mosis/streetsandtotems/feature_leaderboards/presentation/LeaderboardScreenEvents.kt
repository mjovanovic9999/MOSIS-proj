package mosis.streetsandtotems.feature_leaderboards.presentation

import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData

sealed class LeaderboardScreenEvents {
    data class UserAddedToLeaderboardScreen(val leaderboardUserData: LeaderboardUserData) :
        LeaderboardScreenEvents()

    data class LeaderboardUserModifiedScreen(val leaderboardUserData: LeaderboardUserData) :
        LeaderboardScreenEvents()

    data class UserRemovedFromLeaderboardScreen(val leaderboardUserData: LeaderboardUserData) :
        LeaderboardScreenEvents()
}