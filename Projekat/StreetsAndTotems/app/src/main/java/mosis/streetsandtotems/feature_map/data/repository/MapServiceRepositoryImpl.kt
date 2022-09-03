package mosis.streetsandtotems.feature_map.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.model.*
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

    override suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        auth.currentUser?.let {
            firebaseMapDataSource.addCustomPin(l, visible_to, placed_by, text)
        }
    }

    override suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        auth.currentUser?.let {
            firebaseMapDataSource.updateCustomPin(id, visible_to, placed_by, text)
        }
    }

    override suspend fun deleteCustomPin(id: String) {
        auth.currentUser?.let {
            firebaseMapDataSource.deleteCustomPin(id)
        }
    }

    override suspend fun addHome(myId: String, l: GeoPoint) {
        auth.currentUser?.let {
            firebaseMapDataSource.addHome(myId, l)
        }
    }

    override suspend fun deleteHome(myId: String) {
        auth.currentUser?.let {
            firebaseMapDataSource.deleteHome(myId)
        }
    }


    override fun registerCallbacksOnUserInGameDataUpdate(
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit,
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


    override fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData?) -> Unit,
        resourceModifiedCallback: (resource: ResourceData?) -> Unit,
        resourceRemovedCallback: (resource: ResourceData?) -> Unit,
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


    override fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit,
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


    override fun registerCallbackOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData?) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData?) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData?) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseMapDataSource.registerCallbacksOnCustomPinsUpdate(
                    customPinAddedCallback,
                    customPinModifiedCallback,
                    customPinRemovedCallback
                )
            )
        }
    }


    override fun registerCallbackOnHomesUpdate(
        homeAddedCallback: (user: HomeData?) -> Unit,
        homeModifiedCallback: (user: HomeData?) -> Unit,
        homeRemovedCallback: (user: HomeData?) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseMapDataSource.registerCallbacksOnHomesUpdate(
                    homeAddedCallback = homeAddedCallback,
                    homeModifiedCallback = homeModifiedCallback,
                    homeRemovedCallback = homeRemovedCallback,
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