package mosis.streetsandtotems.services.use_case

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.feature_map.domain.model.PinAction
import mosis.streetsandtotems.feature_map.domain.model.PinActionType
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository
import mosis.streetsandtotems.services.LocationServiceInventoryEvents
import mosis.streetsandtotems.services.LocationServiceMainScreenEvents
import mosis.streetsandtotems.services.LocationServiceMapScreenEvents

class RegisterCallbacks(
    private val mapServiceRepository: MapServiceRepository,
    private val locationServiceMapScreenEventsFlow: MutableSharedFlow<LocationServiceMapScreenEvents>,
    private val locationServiceMainScreenEventsFlow: MutableSharedFlow<LocationServiceMainScreenEvents>,
    private val locationServiceInventoryEventsFlow: MutableSharedFlow<LocationServiceInventoryEvents>,
    private val preferenceRepository: PreferenceRepository,
) {
    operator fun invoke() = registerCallbacks()

    private fun emitLocationServiceMapScreenEvent(event: LocationServiceMapScreenEvents) {
        CoroutineScope(Dispatchers.Default).launch {
            locationServiceMapScreenEventsFlow.emit(event)
        }
    }

    private fun emitLocationServiceMainScreenEvent(event: LocationServiceMainScreenEvents) {
        CoroutineScope(Dispatchers.Default).launch {
            locationServiceMainScreenEventsFlow.emit(event)
        }
    }

    private fun emitLocationServiceCommonEvent(event: LocationServiceInventoryEvents) {
        CoroutineScope(Dispatchers.Default).launch {
            locationServiceInventoryEventsFlow.emit(event)
        }
    }

    private fun registerCallbacks() {
        initCurrentUserFlow()
        initUserPinsFlow()
        initResourcesFlow()
        initTotemsFlow()
        initCustomPinsFlow()
        initHomesFlow()
        initUserInventoryFlow()
        initMarketFlow()
    }

    private fun initCurrentUserFlow() {
        mapServiceRepository.registerCallbackOnCurrentUserProfileDataUpdate(currentUserCallback = {
            emitLocationServiceMainScreenEvent(
                LocationServiceMainScreenEvents.CurrentUserProfileDataChanged(
                    it
                )
            )
        })
    }

    private fun initUserPinsFlow() {
        mapServiceRepository.registerCallbacksOnProfileDataUpdate(userAddedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ProfileDataChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        }, userModifiedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ProfileDataChanged(
                    PinAction(
                        it, PinActionType.Modified
                    )
                )
            )
        }, userRemovedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ProfileDataChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        })
    }

    private fun initResourcesFlow() {
        mapServiceRepository.registerCallbackOnResourcesUpdate(resourceAddedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ResourcesChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        }, resourceModifiedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ResourcesChanged(
                    PinAction(
                        it, PinActionType.Modified
                    )
                )
            )
        }, resourceRemovedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.ResourcesChanged(
                    PinAction(
                        it, PinActionType.Removed
                    )
                )
            )
        })
    }

    private fun initTotemsFlow() {
        mapServiceRepository.registerCallbackOnTotemsUpdate(totemAddedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.TotemChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        }, totemModifiedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.TotemChanged(
                    PinAction(
                        it, PinActionType.Modified
                    )
                )
            )
        }, totemRemovedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.TotemChanged(
                    PinAction(
                        it, PinActionType.Removed
                    )
                )
            )
        })
    }

    private fun initCustomPinsFlow() {
        mapServiceRepository.registerCallbackOnCustomPinsUpdate(customPinAddedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.CustomPinChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        }, customPinModifiedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.CustomPinChanged(
                    PinAction(
                        it, PinActionType.Modified
                    )
                )
            )
        }, customPinRemovedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.CustomPinChanged(
                    PinAction(
                        it, PinActionType.Removed
                    )
                )
            )
        })
    }

    private fun initHomesFlow() {
        mapServiceRepository.registerCallbackOnHomesUpdate(
            homeAddedCallback = {
                CoroutineScope(Dispatchers.Default).launch {
                    val userId = preferenceRepository.getUserId()
                    val squadId = preferenceRepository.getSquadId()

                    if (it.id == userId || it.id == squadId) locationServiceMapScreenEventsFlow.emit(
                        LocationServiceMapScreenEvents.HomeChanged(
                            PinAction(
                                it, PinActionType.Added
                            )
                        )
                    )
                }
            }, homeModifiedCallback = {
                CoroutineScope(Dispatchers.Default).launch {
                    val userId = preferenceRepository.getUserId()
                    val squadId = preferenceRepository.getSquadId()

                    if (it.id == userId || it.id == squadId) locationServiceMapScreenEventsFlow.emit(
                        LocationServiceMapScreenEvents.HomeChanged(
                            PinAction(
                                it, PinActionType.Modified
                            )
                        )
                    )
                }
            }, homeRemovedCallback = {
                CoroutineScope(Dispatchers.Default).launch {
                    val userId = preferenceRepository.getUserId()
                    val squadId = preferenceRepository.getSquadId()

                    if (it.id == userId || it.id == squadId) locationServiceMapScreenEventsFlow.emit(
                        LocationServiceMapScreenEvents.HomeChanged(
                            PinAction(
                                it, PinActionType.Removed
                            )
                        )
                    )
                }
            })
    }

    private fun initUserInventoryFlow() {
        mapServiceRepository.registerCallbackOnUserInventoryUpdate {
            Log.d("d", it.toString())
            emitLocationServiceCommonEvent(LocationServiceInventoryEvents.UserInventoryChanged(it))
        }
    }

    private fun initMarketFlow() {
        mapServiceRepository.registerCallbackOnMarketUpdate(marketAddedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.MarketChanged(
                    PinAction(
                        it, PinActionType.Added
                    )
                )
            )
        }, marketModifiedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.MarketChanged(
                    PinAction(
                        it, PinActionType.Modified
                    )
                )
            )
        }, marketRemovedCallback = {
            emitLocationServiceMapScreenEvent(
                LocationServiceMapScreenEvents.MarketChanged(
                    PinAction(
                        it, PinActionType.Removed
                    )
                )
            )
        })
    }
}