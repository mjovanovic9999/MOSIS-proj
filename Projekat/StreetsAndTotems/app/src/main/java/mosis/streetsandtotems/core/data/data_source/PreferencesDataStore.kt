package mosis.streetsandtotems.core.data.data_source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.AUTH_PROVIDER_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.CALL_PRIVACY_LEVEL_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.DATA_STORE_NAME
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.RUN_IN_BACKGROUND_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.SHOW_NOTIFICATIONS_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.SMS_PRIVACY_LEVEL_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.SQUAD_ID_PREFERENCES
import mosis.streetsandtotems.core.PreferencesDataStoreConstants.USER_ID_PREFERENCES
import mosis.streetsandtotems.core.domain.model.PrivacySettings
import mosis.streetsandtotems.core.domain.model.UserSettings


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class PreferencesDataStore(val context: Context) {

    private val _runInBackground = booleanPreferencesKey(RUN_IN_BACKGROUND_PREFERENCES)
    private val _showNotifications = booleanPreferencesKey(SHOW_NOTIFICATIONS_PREFERENCES)
    private val _callPrivacyLevel = intPreferencesKey(CALL_PRIVACY_LEVEL_PREFERENCES)
    private val _smsPrivacyLevel = intPreferencesKey(SMS_PRIVACY_LEVEL_PREFERENCES)
    private val _authProvider = intPreferencesKey(AUTH_PROVIDER_PREFERENCES)

    private val _userId = stringPreferencesKey(USER_ID_PREFERENCES)
    private val _squadId = stringPreferencesKey(SQUAD_ID_PREFERENCES)

    suspend fun saveUserSettings(userSettings: UserSettings) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[_runInBackground] = userSettings.runInBackground
            mutablePreferences[_showNotifications] = userSettings.showNotifications
            mutablePreferences[_callPrivacyLevel] = userSettings.callPrivacyLevel.ordinal
            mutablePreferences[_smsPrivacyLevel] = userSettings.smsPrivacyLevel.ordinal
        }
    }

    suspend fun getUserSettings(): UserSettings {
        return getUserSettingsFlow().first()
    }

    suspend fun getAuthProvider(): AuthProvider {
        return context.dataStore.data.map { AuthProvider.values()[it[_authProvider] ?: 0] }.first()
    }

    suspend fun saveAuthProvider(authProvider: AuthProvider) {
        context.dataStore.edit { it[_authProvider] = authProvider.ordinal }
    }

    fun getUserSettingsFlow(): Flow<UserSettings> {
        return context.dataStore.data.map { preferences ->
            UserSettings(
                runInBackground = preferences[_runInBackground] ?: true,
                showNotifications = preferences[_showNotifications] ?: true,
                callPrivacyLevel = PrivacySettings.values()[preferences[_callPrivacyLevel] ?: 0],
                smsPrivacyLevel = PrivacySettings.values()[preferences[_smsPrivacyLevel] ?: 0]
            )
        }
    }

    suspend fun getUserId(): String {
        return context.dataStore.data.map { it[_userId] ?: "" }.first()
    }

    suspend fun getUserSquadId(): String {
        return context.dataStore.data.map { it[_squadId] ?: "" }.first()
    }

    suspend fun setSquadId(squadId: String) {
        context.dataStore.edit {
            it[_squadId] = squadId
        }
    }

    suspend fun setUserId(userId: String) {
        context.dataStore.edit {
            it[_userId] = userId
        }
    }

    fun getUserSquadIdFlow(): Flow<String> {
        return context.dataStore.data.map { preferences -> preferences[_squadId] ?: "" }
    }
}

enum class AuthProvider {
    None, EmailAndPassword, Google, Facebook
}

