package mosis.streetsandtotems.feature_backpack.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_LAT
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_LNG
import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_backpack.domain.repository.BackpackRepository
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.LocationServiceCommonEvents
import javax.inject.Inject

@HiltViewModel
class BackpackViewModel @Inject constructor(
    private val backpackRepository: BackpackRepository,
    private val locationServiceCommonEventsFlow: SharedFlowWrapper<LocationServiceCommonEvents>
) : ViewModel() {
    private val _backpackScreenState =
        mutableStateOf(BackpackScreenState(UserInventoryData(), DropItemDialogState(false)))
    val backpackScreenState: State<BackpackScreenState> = _backpackScreenState

    init {
        viewModelScope.launch {
            locationServiceCommonEventsFlow.flow.collect {
                when (it) {
                    is LocationServiceCommonEvents.UserInventoryChanged -> onUserInventoryChangedHandler(
                        it.newInventory
                    )
                }
            }
        }
    }

    private fun onUserInventoryChangedHandler(newInventory: UserInventoryData) {
        _backpackScreenState.value =
            _backpackScreenState.value.copy(useInventoryData = newInventory)
    }

    fun openDropItemDialog(
        itemCount: Int,
        resourceType: IconType.ResourceType? = null,
        dropTotem: Boolean = false,
    ) {
        _backpackScreenState.value = _backpackScreenState.value.copy(
            dropItemDialogState = DropItemDialogState(
                open = true,
                itemType = resourceType,
                itemCount = itemCount,
                dropTotem = dropTotem,
            )
        )
    }

    fun closeDropItemDialog() {
        _backpackScreenState.value = _backpackScreenState.value.copy(
            dropItemDialogState = DropItemDialogState(
                open = false,
                itemType = null,
                itemCount = null,
                dropTotem = false,
            )
        )
    }

    fun onEvent(event: BackpackViewModelEvents) {
        when (event) {
            is BackpackViewModelEvents.DropResource -> dropResource(event.itemCount, event.type)
            BackpackViewModelEvents.PlaceTotem -> placeTotem()
        }
    }

    private fun placeTotem() {
        viewModelScope.launch {
            backpackRepository.placeTotem(
                LocationService.lastKnownLocation ?: GeoPoint(
                    INIT_SCROLL_LAT, INIT_SCROLL_LNG
                )
            )
        }
    }

    private fun dropResource(itemCount: Int, type: ResourceType) {
        viewModelScope.launch {
            backpackRepository.dropResource(
                l = LocationService.lastKnownLocation ?: GeoPoint(INIT_SCROLL_LAT, INIT_SCROLL_LNG),
                itemCount = itemCount,
                type = type,
            )
        }
    }
}