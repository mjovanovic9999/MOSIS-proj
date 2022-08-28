package mosis.streetsandtotems.feature_settings_persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mosis.streetsandtotems.core.UserSettingsConstants.CALL_PRIVACY_LEVEL_PREFERENCES
import mosis.streetsandtotems.core.UserSettingsConstants.DATA_STORE_NAME
import mosis.streetsandtotems.core.UserSettingsConstants.RUN_IN_BACKGROUND_PREFERENCES
import mosis.streetsandtotems.core.UserSettingsConstants.SHOW_MY_PHONE_NUMBER_PREFERENCES
import mosis.streetsandtotems.core.UserSettingsConstants.SHOW_NOTIFICATIONS_PREFERENCES
import mosis.streetsandtotems.core.UserSettingsConstants.SMS_PRIVACY_LEVEL_PREFERENCES


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class PreferencesDataStore(val context: Context) {

    private val RUN_IN_BACKGROUND = booleanPreferencesKey(RUN_IN_BACKGROUND_PREFERENCES)
    private val SHOW_NOTIFICATIONS = booleanPreferencesKey(SHOW_NOTIFICATIONS_PREFERENCES)
    private val SHOW_MY_PHONE_NUMBER = booleanPreferencesKey(SHOW_MY_PHONE_NUMBER_PREFERENCES)
    private val CALL_PRIVACY_LEVEL = intPreferencesKey(CALL_PRIVACY_LEVEL_PREFERENCES)
    private val SMS_PRIVACY_LEVEL = intPreferencesKey(SMS_PRIVACY_LEVEL_PREFERENCES)

    suspend fun saveUserSettings(userSettings: UserSettings) {
        context.dataStore.edit { mutablePreferences ->

            mutablePreferences[RUN_IN_BACKGROUND] = userSettings.runInBackground
            mutablePreferences[SHOW_NOTIFICATIONS] = userSettings.showNotifications
            mutablePreferences[SHOW_MY_PHONE_NUMBER] = userSettings.showMyPhoneNumber
            mutablePreferences[CALL_PRIVACY_LEVEL] = userSettings.callPrivacyLevel.ordinal
            mutablePreferences[SMS_PRIVACY_LEVEL] = userSettings.smsPrivacyLevel.ordinal
        }
    }

    val userSettingsFlow: Flow<UserSettings> = context.dataStore.data.map { preferences ->
        UserSettings(
            runInBackground = preferences[RUN_IN_BACKGROUND] ?: true,
            showNotifications = preferences[SHOW_NOTIFICATIONS] ?: true,
            showMyPhoneNumber = preferences[SHOW_MY_PHONE_NUMBER] ?: false,
            callPrivacyLevel = PrivacySettings.values()[preferences[CALL_PRIVACY_LEVEL] ?: 0],
            smsPrivacyLevel = PrivacySettings.values()[preferences[SMS_PRIVACY_LEVEL] ?: 0]
        )
    }

}