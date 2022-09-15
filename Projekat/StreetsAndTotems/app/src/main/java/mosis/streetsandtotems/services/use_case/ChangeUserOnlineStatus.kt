package mosis.streetsandtotems.services.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository
import mosis.streetsandtotems.core.domain.repository.UserOnlineStatusRepository

class ChangeUserOnlineStatus(
    private val userOnlineStatusRepository: UserOnlineStatusRepository,
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(isOnline: Boolean) {
        val userId = preferenceRepository.getUserId()
        if (userId != "") {
            userOnlineStatusRepository.updateUserOnlineStatus(isOnline)
            if (!isOnline) preferenceRepository.setUserId("")
        }
    }
}