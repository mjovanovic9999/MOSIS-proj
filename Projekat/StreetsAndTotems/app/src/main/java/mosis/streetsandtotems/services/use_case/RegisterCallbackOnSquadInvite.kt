package mosis.streetsandtotems.services.use_case

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import mosis.streetsandtotems.services.LocationServiceMapScreenEvents

class RegisterCallbackOnSquadInvite(
    private val mapServiceRepository: MapServiceRepository,
    private val locationServiceMapScreenEventsFlow: MutableSharedFlow<LocationServiceMapScreenEvents>,
) {
    suspend operator fun invoke() {
        mapServiceRepository.registerCallbackOnSquadInviteDataUpdate(squadInviteCallback = {
            CoroutineScope(Dispatchers.Default).launch {
                locationServiceMapScreenEventsFlow.emit(
                    LocationServiceMapScreenEvents.SquadInvite(
                        it
                    )
                )
            }
        })
    }
}