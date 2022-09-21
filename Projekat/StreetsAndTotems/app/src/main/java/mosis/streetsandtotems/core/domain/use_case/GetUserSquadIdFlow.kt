package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class GetUserSquadIdFlow(private val preferenceRepository: PreferenceRepository) {
    operator fun invoke() = preferenceRepository.getUserSquadIdFlow()
}