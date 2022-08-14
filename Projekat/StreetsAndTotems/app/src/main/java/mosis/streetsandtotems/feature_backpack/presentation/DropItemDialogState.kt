package mosis.streetsandtotems.feature_backpack.presentation

enum class ResourceType {
    Emerald,
    Wood,
    Stone,
    Brick
}

data class DropItemDialogState(
    val open: Boolean,
    val itemType: ResourceType? = null,
    val itemCount: Int? = null,
    val dropTotem: Boolean = false,
)