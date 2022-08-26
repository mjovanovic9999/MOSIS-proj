package mosis.streetsandtotems.feature_map.domain.util

sealed interface PinTypes {
    interface ITypeResource : PinTypes
    object TypeResource : ITypeResource
    object TypeResourceWood : ITypeResource
    object TypeResourceBrick : ITypeResource
    object TypeResourceStone : ITypeResource
    object TypeResourceEmerald : ITypeResource

    object TypeTiki : PinTypes
    object TypeFriend : PinTypes
    object TypeMarket : PinTypes
    object TypeHome : PinTypes
    object TypeCustom : PinTypes
    object TypeOtherPlayer : PinTypes
    object TypeOtherHome : PinTypes
    object TypeHomeDiscoveryShot : PinTypes
}