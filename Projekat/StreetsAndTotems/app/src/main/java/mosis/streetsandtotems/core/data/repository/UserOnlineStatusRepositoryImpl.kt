package mosis.streetsandtotems.core.data.repository

import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.core.data.data_source.UserOnlineStatusDataSource
import mosis.streetsandtotems.core.domain.repository.UserOnlineStatusRepository

class UserOnlineStatusRepositoryImpl(
    private val userOnlineStatusDataSource: UserOnlineStatusDataSource,
    private val preferencesDataStore: PreferencesDataStore
) : UserOnlineStatusRepository {
    override suspend fun updateUserOnlineStatus(isOnline: Boolean) {
        try {
            userOnlineStatusDataSource.updateUserOnlineStatus(
                isOnline,
                preferencesDataStore.getUserId()
            ).await()
        } catch (e: Exception) {

        }
    }
}