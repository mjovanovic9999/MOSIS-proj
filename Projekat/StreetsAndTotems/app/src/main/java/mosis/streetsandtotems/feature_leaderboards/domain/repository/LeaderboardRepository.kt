package mosis.streetsandtotems.feature_leaderboards.domain.repository

import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import mosis.streetsandtotems.feature_map.domain.model.ProfileData

interface LeaderboardRepository {
    fun registerCallbacksOnLeaderboardUpdate(
        leaderboardUserAddedCallback: (user: LeaderboardUserData) -> Unit,
        leaderboardUserModifiedCallback: (user: LeaderboardUserData) -> Unit,
        leaderboardUserRemovedCallback: (user: LeaderboardUserData) -> Unit
    )

    fun removeCallbacksOnLeaderboardUpdate()

    suspend fun getUserData(username: String): Flow<Response<ProfileData>>
}