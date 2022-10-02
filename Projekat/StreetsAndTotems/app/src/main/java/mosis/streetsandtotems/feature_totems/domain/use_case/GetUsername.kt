package mosis.streetsandtotems.feature_totems.domain.use_case

import mosis.streetsandtotems.feature_totems.domain.repository.TotemRepository

class GetUsername(private val repository: TotemRepository) {
    suspend operator fun invoke(id: String) = repository.getUsername(id)
}