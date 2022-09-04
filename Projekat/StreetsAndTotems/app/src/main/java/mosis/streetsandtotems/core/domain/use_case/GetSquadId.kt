package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class GetSquadId(private val preferenceRepository: PreferenceRepository) {
    suspend operator fun invoke() = preferenceRepository.getSquadId()
}