package mosis.streetsandtotems.feature_settings_persistence.repository

import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.feature_settings_persistence.data.SettingsPersistence

interface Abstract {

    suspend fun saveSettings(settingsPersistence: SettingsPersistence)

    suspend fun getSettings(): Flow<SettingsPersistence>
}