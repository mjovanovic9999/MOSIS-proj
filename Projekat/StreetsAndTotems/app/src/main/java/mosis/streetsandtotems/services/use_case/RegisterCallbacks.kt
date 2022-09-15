package mosis.streetsandtotems.services.use_case

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.feature_map.domain.model.PinAction
import mosis.streetsandtotems.feature_map.domain.model.PinActionType
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import mosis.streetsandtotems.services.LocationServiceEvents

class RegisterCallbacks(
    private val mapServiceRepository: MapServiceRepository,
    private val locationServiceEventsFlow: MutableSharedFlow<LocationServiceEvents>
) {
    operator fun invoke() = registerCallbacks()

    private fun emitLocationServiceEvent(event: LocationServiceEvents) {
        CoroutineScope(Dispatchers.Default).launch {
            locationServiceEventsFlow.emit(event)
        }
    }

    private fun registerCallbacks() {
        initUserPinsFlow()
        initResourcesFlow()
        initTotemsFlow()
        initCustomPinsFlow()
        initHomesFlow()
        initUserInventoryFlow()
        initMarketFlow()
    }

    private fun initUserPinsFlow() {
        mapServiceRepository.registerCallbacksOnProfileDataUpdate(
            userAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            userModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            userRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ProfileDataChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            }
        )
    }

    private fun initResourcesFlow() {
        mapServiceRepository.registerCallbackOnResourcesUpdate(
            resourceAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            resourceModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            resourceRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.ResourcesChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initTotemsFlow() {
        mapServiceRepository.registerCallbackOnTotemsUpdate(
            totemAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            totemModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            totemRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.TotemChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initCustomPinsFlow() {
        mapServiceRepository.registerCallbackOnCustomPinsUpdate(
            customPinAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            }, customPinModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            }, customPinRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.CustomPinChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initHomesFlow() {
        mapServiceRepository.registerCallbackOnHomesUpdate(
            homeAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            homeModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            homeRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.HomeChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }

    private fun initUserInventoryFlow() {
        mapServiceRepository.registerCallbackOnUserInventoryUpdate {
            Log.d("d", it.toString())
            emitLocationServiceEvent(LocationServiceEvents.UserInventoryChanged(it))
        }
    }

    private fun initMarketFlow() {
        mapServiceRepository.registerCallbackOnMarketUpdate(
            marketAddedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Added
                        )
                    )
                )
            },
            marketModifiedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Modified
                        )
                    )
                )
            },
            marketRemovedCallback = {
                emitLocationServiceEvent(
                    LocationServiceEvents.MarketChanged(
                        PinAction(
                            it,
                            PinActionType.Removed
                        )
                    )
                )
            }
        )
    }
}