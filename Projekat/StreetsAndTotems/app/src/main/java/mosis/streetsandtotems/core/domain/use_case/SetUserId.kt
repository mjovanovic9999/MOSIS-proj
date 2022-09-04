package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class SetUserId(private val preferenceRepository: PreferenceRepository) {
    suspend operator fun invoke(userId: String) = preferenceRepository.setUserId(userId)
}