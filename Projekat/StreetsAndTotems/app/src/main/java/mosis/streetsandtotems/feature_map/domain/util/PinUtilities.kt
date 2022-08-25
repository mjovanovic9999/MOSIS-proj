package mosis.streetsandtotems.feature_map.domain.util

import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.PinConstants.CUSTOM
import mosis.streetsandtotems.core.PinConstants.FRIENDS
import mosis.streetsandtotems.core.PinConstants.HOME
import mosis.streetsandtotems.core.PinConstants.MARKET
import mosis.streetsandtotems.core.PinConstants.OTHER_HOME
import mosis.streetsandtotems.core.PinConstants.OTHER_PLAYER
import mosis.streetsandtotems.core.PinConstants.RESOURCES_BRICKS
import mosis.streetsandtotems.core.PinConstants.RESOURCES_EMERALDS
import mosis.streetsandtotems.core.PinConstants.RESOURCES_STONES
import mosis.streetsandtotems.core.PinConstants.RESOURCES_WOODS
import mosis.streetsandtotems.core.PinConstants.TOTEMS

fun detectPinType(pinId: String): PinTypes {

    if (pinId.startsWith(RESOURCES_WOODS, true)) return PinTypes.TypeResourceWood
    else if (pinId.startsWith(RESOURCES_BRICKS, true)) return PinTypes.TypeResourceBrick
    else if (pinId.startsWith(RESOURCES_STONES, true)) return PinTypes.TypeResourceStone
    else if (pinId.startsWith(RESOURCES_EMERALDS, true)) return PinTypes.TypeResourceEmerald
    else if (pinId.startsWith(TOTEMS, true)) return PinTypes.TypeTiki
    else if (pinId.startsWith(FRIENDS, true)) return PinTypes.TypeFriend
    else if (pinId.startsWith(MARKET, true)) return PinTypes.TypeMarket
    else if (pinId.startsWith(HOME, true)) return PinTypes.TypeHome
    else if (pinId.startsWith(OTHER_HOME, true)) return PinTypes.TypeOtherHome
    else if (pinId.startsWith(OTHER_PLAYER, true)) return PinTypes.TypeOtherPlayer
    else if (pinId.startsWith(CUSTOM, true)) return PinTypes.TypeCustom
    return PinTypes.TypeResource
}


fun returnPinTypeResourceId(pinId: String): Int = when (detectPinType(pinId)) {
    PinTypes.TypeResourceWood -> R.drawable.pin_wood
    PinTypes.TypeResourceBrick -> R.drawable.pin_brick
    PinTypes.TypeResourceStone -> R.drawable.pin_stone
    PinTypes.TypeResourceEmerald -> R.drawable.pin_emerald
    PinTypes.TypeFriend -> R.drawable.pin_friend
    PinTypes.TypeTiki -> R.drawable.pin_tiki
    PinTypes.TypeCustom -> R.drawable.pin_custom
    PinTypes.TypeHome -> R.drawable.pin_home
    PinTypes.TypeHomeDiscoveryShot -> R.drawable.pin_house_discovery_shot
    PinTypes.TypeMarket -> R.drawable.pin_market
    PinTypes.TypeOtherHome -> R.drawable.pin_other_home
    PinTypes.TypeOtherPlayer -> R.drawable.pin_other_player
    else -> R.drawable.menu//bezveze
}