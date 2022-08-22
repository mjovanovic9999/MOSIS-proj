package mosis.streetsandtotems.feature_map.domain.util

sealed interface PinTypes {
    object TypeTiki : PinTypes
    object TypeResource : PinTypes
    object TypeFriend : PinTypes
    object TypeOther : PinTypes
}