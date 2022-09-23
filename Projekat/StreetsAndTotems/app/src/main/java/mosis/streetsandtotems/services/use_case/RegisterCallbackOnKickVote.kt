package mosis.streetsandtotems.services.use_case

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.feature_map.domain.model.KickAction
import mosis.streetsandtotems.feature_map.domain.model.KickActionType
import mosis.streetsandtotems.feature_map.domain.model.Vote
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import mosis.streetsandtotems.services.LocationServiceMapScreenEvents

class RegisterCallbackOnKickVote(
    private val preferenceRepository: PreferenceRepository,
    private val mapServiceRepository: MapServiceRepository,
    private val locationServiceMapScreenEventsFlow: MutableSharedFlow<LocationServiceMapScreenEvents>,
) {
    suspend operator fun invoke() {
        val userId = preferenceRepository.getUserId()
        mapServiceRepository.registerCallbackOnKickVoteDataUpdate(kickVoteStartedCallback = {
            CoroutineScope(Dispatchers.Default).launch {
                if (it.votes?.get(userId) == Vote.Unanswered)
                    locationServiceMapScreenEventsFlow.emit(
                        LocationServiceMapScreenEvents.KickVote(
                            KickAction(KickActionType.VoteStarted, it),
                        )
                    )
            }
        }, kickVoteEndedCallback = {
            CoroutineScope(Dispatchers.Default).launch {
                locationServiceMapScreenEventsFlow.emit(
                    LocationServiceMapScreenEvents.KickVote(
                        KickAction(KickActionType.VoteEnded, it),
                    )
                )
            }
        })
    }
}