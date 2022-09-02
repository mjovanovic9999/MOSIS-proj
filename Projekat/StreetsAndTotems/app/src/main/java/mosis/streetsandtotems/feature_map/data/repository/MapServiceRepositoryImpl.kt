package mosis.streetsandtotems.feature_map.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class MapServiceRepositoryImpl(
    private val firebaseMapDataSource: FirebaseMapDataSource,
    private val auth: FirebaseAuth
) : MapServiceRepository {
    private var listenerRegistrations: MutableList<ListenerRegistration> = mutableListOf()

    override suspend fun updateMyLocation(newLocation: GeoPoint) {
        auth.currentUser?.let {
            firebaseMapDataSource.updateUserLocation(it, newLocation)
        }
    }

    override suspend fun getUserInGameData(): Flow<UserInGameData?> {
        auth.currentUser?.let {
            return firebaseMapDataSource.getUserInGameData(it)
        }
        return emptyFlow()
    }

    override fun registerCallbacksOnUserInGameDataUpdate(
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseMapDataSource.registerCallbacksOnUserInGameDataUpdate(
                    it,
                    userAddedCallback = userAddedCallback,
                    userModifiedCallback = userModifiedCallback,
                    userRemovedCallback = userRemovedCallback
                )
            )
        }
    }


    override suspend fun getResources(): Flow<ResourceData?> {
        auth.currentUser?.let {
            return firebaseMapDataSource.getResources()
        }
        return emptyFlow()
    }

    override fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData?) -> Unit,
        resourceModifiedCallback: (resource: ResourceData?) -> Unit,
        resourceRemovedCallback: (resource: ResourceData?) -> Unit
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseMapDataSource.registerCallbacksOnResourcesUpdate(
                    resourceAddedCallback,
                    resourceModifiedCallback,
                    resourceRemovedCallback
                )
            )
        }
    }


    override suspend fun getTotems(): Flow<TotemData?> {
        auth.currentUser?.let {
            return firebaseMapDataSource.getTotems()
        }
        return emptyFlow()
    }

    override fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseMapDataSource.registerCallbacksOnTotemsUpdate(
                    totemAddedCallback,
                    totemModifiedCallback,
                    totemRemovedCallback
                )
            )
        }
    }


    override fun removeAllCallbacks() {
        listenerRegistrations.forEach {
            it.remove()
        }
    }
}