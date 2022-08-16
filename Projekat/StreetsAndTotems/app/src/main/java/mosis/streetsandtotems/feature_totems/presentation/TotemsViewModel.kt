package mosis.streetsandtotems.feature_totems.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.feature_totems.domain.model.Totem
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TotemsViewModel @Inject constructor() : ViewModel() {
    private val _totemsState =
        mutableStateOf(
            TotemsState(
                listOf(
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),
                    Totem("12345", "KorisnickoIme1", LocalDateTime.now(), LocalDateTime.now()),

                    ),
                false,
                null
            )
        )
    val totemsState: State<TotemsState> = _totemsState

    fun showDialog(clickedIndex: Int) {
        _totemsState.value =
            _totemsState.value.copy(dialogOpen = true, selectedTotem = clickedIndex)
    }

    fun closeDialog() {
        _totemsState.value = _totemsState.value.copy(dialogOpen = false, selectedTotem = null)
    }
}