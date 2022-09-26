package mosis.streetsandtotems.feature_leaderboards.domain.use_case

import mosis.streetsandtotems.feature_leaderboards.domain.repository.LeaderboardRepository

class GetUserData(private val leaderboardRepository: LeaderboardRepository) {
    suspend operator fun invoke(username: String) = leaderboardRepository.getUserData(username)
}