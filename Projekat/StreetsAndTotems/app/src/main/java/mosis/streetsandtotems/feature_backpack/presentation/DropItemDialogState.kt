package mosis.streetsandtotems.feature_backpack.presentation

import mosis.streetsandtotems.core.presentation.components.IconType


data class DropItemDialogState(
    val open: Boolean=false,
    val itemType: IconType.ResourceType? = null,
    val itemCount: Int = 0,
)