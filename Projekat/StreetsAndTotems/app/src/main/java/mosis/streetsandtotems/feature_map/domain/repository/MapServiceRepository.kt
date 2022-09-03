package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.feature_map.domain.model.*

interface MapServiceRepository {
    suspend fun updateMyLocation(newLocation: GeoPoint)

    suspend fun addCustomPin(l: GeoPoint, visible_to: String, placed_by: String, text: String)

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    )

    suspend fun deleteCustomPin(id: String)

    suspend fun addHome(myId: String, l: GeoPoint)

    suspend fun deleteHome(myId: String)

   fun registerCallbacksOnUserInGameDataUpdate(
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    )



    fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData?) -> Unit,
        resourceModifiedCallback: (resource: ResourceData?) -> Unit,
        resourceRemovedCallback: (resource: ResourceData?) -> Unit,
    )



    fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit,
    )



    fun registerCallbackOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData?) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData?) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData?) -> Unit,
    )



    fun registerCallbackOnHomesUpdate(
        homeAddedCallback: (home: HomeData?) -> Unit,
        homeModifiedCallback: (home: HomeData?) -> Unit,
        homeRemovedCallback: (home: HomeData?) -> Unit,
    )

    //fun registerCallbackOnUserDataUpdate()

    //fun getCustomPins(): Flow<>
    fun removeAllCallbacks()
}