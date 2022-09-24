package mosis.streetsandtotems.feature_backpack.presentation

import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

data class BackpackScreenState(
    val useInventoryData: UserInventoryData, val dropItemDialogState: DropItemDialogState
)
