package mosis.streetsandtotems.feature_leaderboards.presentation

sealed class LeaderboardViewModelEvents {
    object ClosePlayerDialog : LeaderboardViewModelEvents()
    object RemoveCallbacks : LeaderboardViewModelEvents()
    data class ShowPlayerDialog(val username: String) : LeaderboardViewModelEvents()
}