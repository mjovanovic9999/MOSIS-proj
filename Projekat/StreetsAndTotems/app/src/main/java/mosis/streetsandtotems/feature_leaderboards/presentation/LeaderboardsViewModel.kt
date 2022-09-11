package mosis.streetsandtotems.feature_leaderboards.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import javax.inject.Inject

@HiltViewModel
class LeaderboardsViewModel @Inject constructor() : ViewModel() {
    private val _leaderboardState =
        mutableStateOf(
            LeaderboardsState(
                listOf(
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                    LeaderboardUserData("KorisnickoIme1", 12345),
                ),
                playerDialogOpen = false
            ),

            )
    val leaderboardState: State<LeaderboardsState> = _leaderboardState

    fun showPlayerDialog() {
        _leaderboardState.value = _leaderboardState.value.copy(playerDialogOpen = true)
    }

    fun closePlayerDialog() {
        _leaderboardState.value = _leaderboardState.value.copy(playerDialogOpen = false)
    }
}