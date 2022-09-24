package mosis.streetsandtotems.feature_backpack.presentation

import mosis.streetsandtotems.core.presentation.components.IconType
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.presentation.MapViewModelEvents

sealed class BackpackViewModelEvents {
    object PlaceTotem : BackpackViewModelEvents()
    data class DropResource(
        val dropItemCount: Int,
        val type: IconType.ResourceType?,
    ) : BackpackViewModelEvents()

}
