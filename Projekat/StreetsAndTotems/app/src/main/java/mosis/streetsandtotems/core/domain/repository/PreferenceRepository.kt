package mosis.streetsandtotems.core.domain.repository

import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.domain.model.UserSettings

interface PreferenceRepository {
    suspend fun getUserSettings(): UserSettings

    suspend fun updateUserSettings(userSettings: UserSettings)

    suspend fun getAuthProvider(): AuthProvider

    suspend fun saveAuthProvider(authProvider: AuthProvider)

    fun getUserSettingsFlow(): Flow<UserSettings>

    suspend fun getUserId(): String

    suspend fun getSquadId(): String

    suspend fun setUserId(userId: String)

    suspend fun setSquadId(squadId: String)

}