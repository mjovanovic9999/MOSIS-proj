package mosis.streetsandtotems.services

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*

sealed class LocationServiceEvents {
    data class PlayerLocationChanged(val newLocation: GeoPoint) : LocationServiceEvents()
    sealed class PinDataChanged<T : IData>(val pinAction: PinAction<T>) : LocationServiceEvents()
    class UserInGameDataChanged(pinAction: PinAction<UserInGameData>) :
        PinDataChanged<UserInGameData>(pinAction)

    data class ResourcesChanged(val resourcePinAction: PinAction<ResourceData>) :
        PinDataChanged<ResourceData>(resourcePinAction)

    data class TotemChanged(val totemPinAction: PinAction<TotemData>) :
        PinDataChanged<TotemData>(totemPinAction)


    data class CustomPinChanged(val customPinPinAction: PinAction<CustomPinData>) :
        PinDataChanged<CustomPinData>(customPinPinAction)

    data class HomeChanged(val homePinAction: PinAction<HomeData>) :
        PinDataChanged<HomeData>(homePinAction)
}
