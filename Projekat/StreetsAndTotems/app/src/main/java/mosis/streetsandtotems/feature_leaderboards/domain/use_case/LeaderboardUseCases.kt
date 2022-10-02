package mosis.streetsandtotems.feature_leaderboards.domain.use_case

data class LeaderboardUseCases(
    val registerLeaderboardCallback: RegisterLeaderboardCallback,
    val removeLeaderboardCallback: RemoveLeaderboardCallback,
    val getUserData: GetUserData
)