package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class GetUserSettingsFlow(private val preferenceRepository: PreferenceRepository) {
    operator fun invoke() = preferenceRepository.getUserSettingsFlow()
}