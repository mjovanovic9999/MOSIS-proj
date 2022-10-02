package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class SetSquadId(private val preferenceRepository: PreferenceRepository) {
    suspend operator fun invoke(squadId: String) = preferenceRepository.setSquadId(squadId)
}