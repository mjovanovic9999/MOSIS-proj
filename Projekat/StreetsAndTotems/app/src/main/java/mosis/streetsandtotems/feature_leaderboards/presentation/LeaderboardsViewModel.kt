package mosis.streetsandtotems.feature_leaderboards.presentation

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.HandleResponseConstants
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.presentation.components.SnackbarType
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_leaderboards.domain.use_case.LeaderboardUseCases
import javax.inject.Inject

@HiltViewModel
class LeaderboardsViewModel @Inject constructor(
    private val leaderboardScreenEventsFlow: SharedFlowWrapper<LeaderboardScreenEvents>,
    private val leaderboardUseCases: LeaderboardUseCases,
    private val showLoaderFlow: MutableStateFlow<Boolean>,
    private val snackbarFlow: MutableStateFlow<SnackbarSettings?>,
    private val preferencesDataStore: PreferencesDataStore,
) : ViewModel() {
    private val _leaderboardState = mutableStateOf(
        LeaderboardsState(
            emptyList(), playerDialogOpen = false, null,
            mySquadId = "", myId = "",
        ),

        )


    init {
        leaderboardUseCases.registerLeaderboardCallback()
        viewModelScope.launch {
            leaderboardScreenEventsFlow.flow.collect {
                when (it) {
                    is LeaderboardScreenEvents.LeaderboardUserModifiedScreen -> {
                        val newUsersList = _leaderboardState.value.leaderboardUsers.toMutableList()
                        newUsersList.sortByDescending { user -> user.points }
                        _leaderboardState.value =
                            _leaderboardState.value.copy(leaderboardUsers = newUsersList.map { user -> if (user.username == it.leaderboardUserData.username) it.leaderboardUserData else user })
                    }
                    is LeaderboardScreenEvents.UserAddedToLeaderboardScreen -> {
                        val newUsersList = _leaderboardState.value.leaderboardUsers.toMutableList()
                        newUsersList.add(it.leaderboardUserData)
                        newUsersList.sortByDescending { user -> user.points }
                        _leaderboardState.value = _leaderboardState.value.copy(
                            leaderboardUsers = newUsersList
                        )
                    }
                    is LeaderboardScreenEvents.UserRemovedFromLeaderboardScreen -> {
                        val newUsersList = _leaderboardState.value.leaderboardUsers.toMutableList()
                        newUsersList.remove(it.leaderboardUserData)
                        newUsersList.sortByDescending { user -> user.points }
                        _leaderboardState.value =
                            _leaderboardState.value.copy(leaderboardUsers = newUsersList)
                    }
                }
            }
        }
        viewModelScope.launch {
            preferencesDataStore.getUserSquadIdFlow().collect {
                _leaderboardState.value = _leaderboardState.value.copy(
                    mySquadId = it,
                )
            }
        }
        viewModelScope.launch {
            _leaderboardState.value = _leaderboardState.value.copy(
                myId = preferencesDataStore.getUserId(),
            )
            Log.d("id", _leaderboardState.value.myId)
        }
    }

    fun onEvent(event: LeaderboardViewModelEvents) {
        when (event) {
            LeaderboardViewModelEvents.ClosePlayerDialog -> onClosePlayerDialog()
            LeaderboardViewModelEvents.RemoveCallbacks -> onRemoveCallbacksHandler()
            is LeaderboardViewModelEvents.ShowPlayerDialog -> onShowPlayerDialogHandler(event.username)
        }
    }

    val leaderboardState: State<LeaderboardsState> = _leaderboardState


    private fun onRemoveCallbacksHandler() {
        leaderboardUseCases.removeLeaderboardCallback()
    }

    private fun onShowPlayerDialogHandler(username: String) {
        viewModelScope.launch {
            leaderboardUseCases.getUserData(username).collect {
                when (it) {
                    is Response.Error -> {
                        showLoaderFlow.emit(false)
                        it.message?.let { message ->
                            snackbarFlow.emit(
                                SnackbarSettings(
                                    message = message,
                                    duration = SnackbarDuration.Short,
                                    snackbarType = SnackbarType.Error,
                                    snackbarId = snackbarFlow.value?.snackbarId?.plus(other = HandleResponseConstants.ID_ADDITION_FACTOR)
                                        ?: HandleResponseConstants.DEFAULT_ID
                                )
                            )
                        }
                    }
                    Response.Loading -> showLoaderFlow.emit(true)
                    is Response.Success -> {
                        showLoaderFlow.emit(false)
                        _leaderboardState.value = _leaderboardState.value.copy(
                            playerDialogOpen = true, playerDialogData = it.data
                        )
                    }
                }
            }
        }
    }


    private fun onClosePlayerDialog() {
        _leaderboardState.value = _leaderboardState.value.copy(playerDialogOpen = false)
    }
}