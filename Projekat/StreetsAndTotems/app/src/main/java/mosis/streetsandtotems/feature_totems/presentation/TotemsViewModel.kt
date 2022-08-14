package mosis.streetsandtotems.feature_totems.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.feature_totems.domain.model.Totem
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TotemsViewModel @Inject constructor() : ViewModel() {
    private val _totemsState =
        mutableStateOf(
            TotemsState(
                listOf(
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),
                    Totem("12345", "KorisnickoIme1", Date(12), Date(12)),

                    )
            )
        )
    val totemsState: State<TotemsState> = _totemsState


}