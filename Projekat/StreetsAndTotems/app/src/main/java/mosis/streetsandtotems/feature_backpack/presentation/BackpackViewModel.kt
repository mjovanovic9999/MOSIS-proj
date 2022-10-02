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
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.LocationServiceInventoryEvents
import javax.inject.Inject

@HiltViewModel
class BackpackViewModel @Inject constructor(
    private val backpackRepository: BackpackRepository,
    private val locationServiceInventoryEventsFlow: SharedFlowWrapper<LocationServiceInventoryEvents>
) : ViewModel() {
    private val _backpackScreenState =
        mutableStateOf(BackpackScreenState(UserInventoryData(), DropItemDialogState(false)))
    val backpackScreenState: State<BackpackScreenState> = _backpackScreenState

    fun onEvent(event: BackpackViewModelEvents) {
        when (event) {
            is BackpackViewModelEvents.DropResource -> dropResource(
                event.dropItemCount,
                event.type,
            )
        }
    }


    init {
        viewModelScope.launch {
            locationServiceInventoryEventsFlow.flow.collect {
                when (it) {
                    is LocationServiceInventoryEvents.UserInventoryChanged -> onUserInventoryChangedHandler(
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
    ) {
        _backpackScreenState.value = _backpackScreenState.value.copy(
            dropItemDialogState = DropItemDialogState(
                open = true,
                itemType = resourceType,
                itemCount = itemCount,
            )
        )
    }

    fun closeDropItemDialog() {
        _backpackScreenState.value = _backpackScreenState.value.copy(
            dropItemDialogState = DropItemDialogState(
                open = false,
                itemType = null,
                itemCount = 0,
            )
        )
    }


    private fun dropResource(dropItemCount: Int, type: IconType.ResourceType?) {
        viewModelScope.launch {
            backpackRepository.dropItem(
                l = LocationService.lastKnownLocation ?: GeoPoint(INIT_SCROLL_LAT, INIT_SCROLL_LNG),
                itemCount = dropItemCount,
                type = type,
                oldInventory = backpackScreenState.value.useInventoryData,
            )
            closeDropItemDialog()
        }

    }
}