package mosis.streetsandtotems.feature_totems.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.feature_totems.domain.use_case.TotemUseCases
import javax.inject.Inject

@HiltViewModel
class TotemsViewModel @Inject constructor(
    private val totemUseCases: TotemUseCases,
    private val preferenceRepository: PreferenceRepository,
    private val selectedTotemIdFlow: MutableSharedFlow<String>
) : ViewModel() {
    private val _totemsState = mutableStateOf(
        TotemsState(
            emptyList(), false, null
        )
    )

    init {
        totemUseCases.registerTotemCallbacks({
            viewModelScope.launch {
                val userId = preferenceRepository.getUserId()
                val squadId = preferenceRepository.getSquadId()

                if (it.placed_by == userId && it.visible_to == squadId) {
                    val username = totemUseCases.getUsername(it.placed_by)
                    val newList = _totemsState.value.totems.toMutableList()
                    newList.add(it.copy(placed_by = username))
                    _totemsState.value = _totemsState.value.copy(totems = newList)
                }
            }
        }, {
            val newList = _totemsState.value.totems.toMutableList()
                .map { totem -> if (totem.id == it.id) it.copy(placed_by = totem.placed_by) else totem }
            _totemsState.value = _totemsState.value.copy(totems = newList)
        }, {
            val newList = _totemsState.value.totems.toMutableList()
            newList.remove(it)
            _totemsState.value = _totemsState.value.copy(totems = newList)
        })
    }

    val totemsState: State<TotemsState> = _totemsState

    fun onEvent(event: TotemViewModelEvents) {
        when (event) {
            TotemViewModelEvents.CloseDialog -> onCloseDialogHandler()
            TotemViewModelEvents.RemoveCallbacks -> totemUseCases.removeTotemCallbacks()
            is TotemViewModelEvents.ShowDialog -> onShowDialogHandler(event.index)
            is TotemViewModelEvents.SelectTotem -> onSelectTotemHandler()
        }
    }

    private fun onSelectTotemHandler() {
        if (_totemsState.value.selectedTotem != null) {
            _totemsState.value.totems[_totemsState.value.selectedTotem!!].id?.let {
                viewModelScope.launch {
                    onCloseDialogHandler()
                    selectedTotemIdFlow.emit(it)
                }
            }
        }
    }

    private fun onShowDialogHandler(clickedIndex: Int) {
        _totemsState.value =
            _totemsState.value.copy(dialogOpen = true, selectedTotem = clickedIndex)
    }

    private fun onCloseDialogHandler() {
        _totemsState.value = _totemsState.value.copy(dialogOpen = false, selectedTotem = null)
    }
}