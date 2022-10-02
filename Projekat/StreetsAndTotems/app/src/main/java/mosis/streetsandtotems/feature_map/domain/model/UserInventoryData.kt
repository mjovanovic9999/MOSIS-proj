package mosis.streetsandtotems.feature_map.domain.model


data class UserInventoryData(
    val inventory: InventoryData? = null,
    val empty_spaces: Int? = null,
)


data class InventoryData(
    val emerald: Int? = null,
    val stone: Int? = null,
    val brick: Int? = null,
    val wood: Int? = null,
    val totem: Int? = null
)