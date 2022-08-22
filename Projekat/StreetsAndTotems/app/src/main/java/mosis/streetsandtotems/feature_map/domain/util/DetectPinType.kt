package mosis.streetsandtotems.feature_map.domain.util

import mosis.streetsandtotems.core.PinConstants.FRIENDS
import mosis.streetsandtotems.core.PinConstants.RESOURCES
import mosis.streetsandtotems.core.PinConstants.TIKIS
import mosis.streetsandtotems.feature_map.domain.model.PinDTO

fun detectPinType(pin: PinDTO): PinTypes {

    if (pin.id.contains(RESOURCES, true)) {
        return PinTypes.TypeResource
    } else if (pin.id.contains(TIKIS, true)) {
        return PinTypes.TypeTiki
    } else if (pin.id.contains(FRIENDS, true)) {
        return PinTypes.TypeFriend
    }
    return PinTypes.TypeOther
}