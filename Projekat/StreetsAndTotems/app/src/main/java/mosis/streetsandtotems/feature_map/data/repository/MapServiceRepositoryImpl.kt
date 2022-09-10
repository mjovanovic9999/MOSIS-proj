package mosis.streetsandtotems.feature_map.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseServiceDataSource
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class MapServiceRepositoryImpl(
    private val firebaseServiceDataSource: FirebaseServiceDataSource,
    private val auth: FirebaseAuth
) : MapServiceRepository {
    private var listenerRegistrations: MutableList<ListenerRegistration> = mutableListOf()

    override suspend fun updateMyLocation(newLocation: GeoPoint) {
        auth.currentUser?.let {
            firebaseServiceDataSource.updateUserLocation(it, newLocation)
        }
    }


    override fun registerCallbacksOnProfileDataUpdate(
        userAddedCallback: (user: ProfileData) -> Unit,
        userModifiedCallback: (user: ProfileData) -> Unit,
        userRemovedCallback: (user: ProfileData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnProfileDataUpdate(
                    it,
                    userAddedCallback = userAddedCallback,
                    userModifiedCallback = userModifiedCallback,
                    userRemovedCallback = userRemovedCallback
                )
            )
        }
    }


    override fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData) -> Unit,
        resourceModifiedCallback: (resource: ResourceData) -> Unit,
        resourceRemovedCallback: (resource: ResourceData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnResourcesUpdate(
                    resourceAddedCallback,
                    resourceModifiedCallback,
                    resourceRemovedCallback
                )
            )
        }
    }


    override fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData) -> Unit,
        totemModifiedCallback: (totem: TotemData) -> Unit,
        totemRemovedCallback: (totem: TotemData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnTotemsUpdate(
                    totemAddedCallback,
                    totemModifiedCallback,
                    totemRemovedCallback
                )
            )
        }
    }


    override fun registerCallbackOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnCustomPinsUpdate(
                    customPinAddedCallback,
                    customPinModifiedCallback,
                    customPinRemovedCallback
                )
            )
        }
    }


    override fun registerCallbackOnHomesUpdate(
        homeAddedCallback: (home: HomeData) -> Unit,
        homeModifiedCallback: (home: HomeData) -> Unit,
        homeRemovedCallback: (home: HomeData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnHomesUpdate(
                    homeAddedCallback = homeAddedCallback,
                    homeModifiedCallback = homeModifiedCallback,
                    homeRemovedCallback = homeRemovedCallback,
                )
            )
        }
    }

    override fun registerCallbackOnUserInventoryUpdate(userInventoryCallback: (userInventoryData: UserInventoryData) -> Unit) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnUserInventoryUpdate(
                    userInventoryCallback
                )
            )
        }
    }

    override fun registerCallbackOnMarketUpdate(
        marketAddedCallback: (market: MarketData) -> Unit,
        marketModifiedCallback: (market: MarketData) -> Unit,
        marketRemovedCallback: (market: MarketData) -> Unit,
    ) {
        auth.currentUser?.let {
            listenerRegistrations.add(
                firebaseServiceDataSource.registerCallbacksOnMarketUpdate(
                    marketAddedCallback = marketAddedCallback,
                    marketModifiedCallback = marketModifiedCallback,
                    marketRemovedCallback = marketRemovedCallback,
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