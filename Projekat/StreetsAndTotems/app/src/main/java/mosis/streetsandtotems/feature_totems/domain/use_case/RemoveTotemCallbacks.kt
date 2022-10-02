package mosis.streetsandtotems.feature_totems.domain.use_case

import mosis.streetsandtotems.feature_totems.domain.repository.TotemRepository

class RemoveTotemCallbacks(private val repository: TotemRepository) {
    operator fun invoke() = repository.removeCallbacksOnTotemDataUpdate()
}