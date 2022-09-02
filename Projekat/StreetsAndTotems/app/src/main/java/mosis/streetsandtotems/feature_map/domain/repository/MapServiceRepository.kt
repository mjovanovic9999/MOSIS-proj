package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData

interface MapServiceRepository {
    suspend fun updateMyLocation(newLocation: GeoPoint)

    suspend fun getUserInGameData(): Flow<UserInGameData?>

    fun registerCallbacksOnUserInGameDataUpdate(
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    )


    suspend fun getResources(): Flow<ResourceData?>

    fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData?) -> Unit,
        resourceModifiedCallback: (resource: ResourceData?) -> Unit,
        resourceRemovedCallback: (resource: ResourceData?) -> Unit
    )


    suspend fun getTotems(): Flow<TotemData?>

    fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit
    )


    //fun registerCallbackOnUserDataUpdate()

    //fun getCustomPins(): Flow<>
    fun removeAllCallbacks()
}