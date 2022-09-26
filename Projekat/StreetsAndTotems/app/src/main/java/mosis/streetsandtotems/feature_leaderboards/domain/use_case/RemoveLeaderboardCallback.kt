package mosis.streetsandtotems.feature_leaderboards.domain.use_case

import mosis.streetsandtotems.feature_leaderboards.domain.repository.LeaderboardRepository

class RemoveLeaderboardCallback(private val leaderboardRepository: LeaderboardRepository) {
    operator fun invoke() = leaderboardRepository.removeCallbacksOnLeaderboardUpdate()
}