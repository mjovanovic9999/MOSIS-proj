package mosis.streetsandtotems.feature_settings_persistence.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mosis.streetsandtotems.feature_settings_persistence.data.SettingsPersistence
import mosis.streetsandtotems.feature_settings_persistence.repository.RepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SettingsPersistenceViewModel @Inject constructor(private val implRepository: RepositoryImpl) :
    ViewModel() {

    var runInBackground: MutableLiveData<String> = MutableLiveData("x")

    var settingsPersistence: MutableLiveData<SettingsPersistence> = MutableLiveData()

    fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
            implRepository.saveSettings(
                SettingsPersistence(
                    runInBackground = runInBackground.value!!,
                )

            )
        }
    }

    fun retrieveDate() {
        viewModelScope.launch(Dispatchers.IO) {
            implRepository.getSettings().collect {
                settingsPersistence.postValue(it)
                Log.d("tag", it.runInBackground.toString() + "procitao cb")

            }
        }
    }
}