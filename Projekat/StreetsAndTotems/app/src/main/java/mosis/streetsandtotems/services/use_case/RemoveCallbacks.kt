package mosis.streetsandtotems.services.use_case

import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class RemoveCallbacks(private val mapServiceRepository: MapServiceRepository) {
    operator fun invoke() = mapServiceRepository.removeAllCallbacks()
}