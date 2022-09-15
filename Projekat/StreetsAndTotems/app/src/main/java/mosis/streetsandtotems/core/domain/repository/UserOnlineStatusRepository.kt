package mosis.streetsandtotems.core.domain.repository

interface UserOnlineStatusRepository {
    suspend fun updateUserOnlineStatus(isOnline: Boolean)
}