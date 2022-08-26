package mosis.streetsandtotems.feature_settings_persistence.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import mosis.streetsandtotems.feature_settings_persistence.data.SettingsPersistence


const val DataStore_NAME = "SETTINGS"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)


class RepositoryImpl(private val context: Context) : Abstract {

    companion object {
        val RUN_IN_BACKGROUND = stringPreferencesKey("RUN_IN_BACKGROUND")
    }

    override suspend fun saveSettings(settingsPersistence: SettingsPersistence) {
        context.datastore.edit { sett ->
            sett[RUN_IN_BACKGROUND] = settingsPersistence.runInBackground

        }

    }

    override suspend fun getSettings() = context.datastore.data.map { sett ->
        SettingsPersistence(
            runInBackground = sett[RUN_IN_BACKGROUND]!!,
        )
    }
}
