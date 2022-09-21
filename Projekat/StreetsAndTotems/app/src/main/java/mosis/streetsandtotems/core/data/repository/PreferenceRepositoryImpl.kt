package mosis.streetsandtotems.core.data.repository

import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.domain.model.UserSettings
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl(private val preferenceDataStore: PreferencesDataStore) :
    PreferenceRepository {
    override suspend fun getUserSettings(): UserSettings {
        return preferenceDataStore.getUserSettings()
    }

    override suspend fun updateUserSettings(userSettings: UserSettings) {
        preferenceDataStore.saveUserSettings(userSettings = userSettings)
    }

    override suspend fun getAuthProvider(): AuthProvider {
        return preferenceDataStore.getAuthProvider()
    }

    override suspend fun saveAuthProvider(authProvider: AuthProvider) {
        preferenceDataStore.saveAuthProvider(authProvider = authProvider)
    }

    override fun getUserSettingsFlow(): Flow<UserSettings> {
        return preferenceDataStore.getUserSettingsFlow()
    }

    override suspend fun getUserId(): String {
        return preferenceDataStore.getUserId()
    }

    override suspend fun getSquadId(): String {
        return preferenceDataStore.getUserSquadId()
    }

    override suspend fun setUserId(userId: String) {
        return preferenceDataStore.setUserId(userId)
    }

    override suspend fun setSquadId(squadId: String) {
        return preferenceDataStore.setSquadId(squadId)
    }

    override fun getUserSquadIdFlow(): Flow<String> {
        return preferenceDataStore.getUserSquadIdFlow()
    }

}