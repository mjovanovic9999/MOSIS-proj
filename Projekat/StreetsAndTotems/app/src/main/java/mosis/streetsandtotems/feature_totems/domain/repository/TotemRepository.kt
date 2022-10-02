package mosis.streetsandtotems.feature_totems.domain.repository

import mosis.streetsandtotems.feature_map.domain.model.TotemData

interface TotemRepository {
    fun registerCallbacksOnTotemDataUpdate(
        totemAdded: (TotemData) -> Unit,
        totemModified: (TotemData) -> Unit,
        totemDeleted: (TotemData) -> Unit
    )

    fun removeCallbacksOnTotemDataUpdate()

    suspend fun getUsername(id: String): String
}