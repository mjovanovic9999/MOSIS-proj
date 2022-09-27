package mosis.streetsandtotems.feature_totems.domain.use_case

import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_totems.domain.repository.TotemRepository

class RegisterTotemCallbacks(private val repository: TotemRepository) {
    operator fun invoke(
        totemAdded: (TotemData) -> Unit,
        totemModified: (TotemData) -> Unit,
        totemDeleted: (TotemData) -> Unit
    ) = repository.registerCallbacksOnTotemDataUpdate(totemAdded, totemModified, totemDeleted)
}