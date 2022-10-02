package mosis.streetsandtotems.feature_leaderboards.domain.use_case

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.feature_leaderboards.domain.repository.LeaderboardRepository
import mosis.streetsandtotems.feature_leaderboards.presentation.LeaderboardScreenEvents

class RegisterLeaderboardCallback(
    private val leaderboardRepository: LeaderboardRepository,
    private val leaderboardScreenEventsFlow: MutableSharedFlow<LeaderboardScreenEvents>
) {
    operator fun invoke() = leaderboardRepository.registerCallbacksOnLeaderboardUpdate({
        CoroutineScope(Dispatchers.Default).launch {
            leaderboardScreenEventsFlow.emit(
                LeaderboardScreenEvents.UserAddedToLeaderboardScreen(
                    it
                )
            )
        }
    }, {
        CoroutineScope(Dispatchers.Default).launch {
            leaderboardScreenEventsFlow.emit(
                LeaderboardScreenEvents.LeaderboardUserModifiedScreen(
                    it
                )
            )
        }
    }, {
        CoroutineScope(Dispatchers.Default).launch {
            leaderboardScreenEventsFlow.emit(
                LeaderboardScreenEvents.UserRemovedFromLeaderboardScreen(
                    it
                )
            )
        }
    })
}