package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.feature_map.domain.model.Resource
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData

interface MapServiceRepository {
    suspend fun updateMyLocation(newLocation: GeoPoint)
    fun registerCallbacksOnUserInGameDataUpdate(
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    )

    suspend fun getUserInGameData(): Flow<UserInGameData?>

    suspend fun getResources(): Flow<Resource?>

    fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: Resource?) -> Unit,
        resourceModifiedCallback: (resource: Resource?) -> Unit,
        resourceRemovedCallback: (resource: Resource?) -> Unit
    )

    fun removeAllCallbacks()
}