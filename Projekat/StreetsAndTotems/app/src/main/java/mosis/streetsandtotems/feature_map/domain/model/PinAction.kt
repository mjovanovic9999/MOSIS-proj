package mosis.streetsandtotems.feature_map.domain.model

enum class PinActionType() {
    Added,
    Modified,
    Removed
}

data class PinAction<T>(val pinData: T, val action: PinActionType)