package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.model.UserSettings
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class UpdateUserSettings(private val preferenceRepository: PreferenceRepository) {
    suspend operator fun invoke(userSettings: UserSettings) =
        preferenceRepository.updateUserSettings(userSettings = userSettings)
}