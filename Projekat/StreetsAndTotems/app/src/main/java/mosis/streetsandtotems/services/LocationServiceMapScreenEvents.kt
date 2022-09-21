package mosis.streetsandtotems.services

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*

sealed class LocationServiceMapScreenEvents {
    data class UserInventoryChanged(val newInventory: UserInventoryData) :
        LocationServiceMapScreenEvents()

    data class PlayerMapScreenLocationChanged(val newLocation: GeoPoint) :
        LocationServiceMapScreenEvents()

    data class SquadInvite(val squadInvite: SquadInviteData) : LocationServiceMapScreenEvents()
    data class KickVote(val kickAction: KickAction) : LocationServiceMapScreenEvents()

    sealed class PinDataChanged<T : Data>(val pinAction: PinAction<T>) :
        LocationServiceMapScreenEvents()

    class ProfileDataChanged(pinAction: PinAction<ProfileData>) :
        PinDataChanged<ProfileData>(pinAction)

    data class ResourcesChanged(val resourcePinAction: PinAction<ResourceData>) :
        PinDataChanged<ResourceData>(resourcePinAction)

    data class TotemChanged(val totemPinAction: PinAction<TotemData>) :
        PinDataChanged<TotemData>(totemPinAction)

    data class CustomPinChanged(val customPinPinAction: PinAction<CustomPinData>) :
        PinDataChanged<CustomPinData>(customPinPinAction)

    data class HomeChanged(val homePinAction: PinAction<HomeData>) :
        PinDataChanged<HomeData>(homePinAction)

    data class MarketChanged(val marketPinAction: PinAction<MarketData>) :
        PinDataChanged<MarketData>(marketPinAction)
}
