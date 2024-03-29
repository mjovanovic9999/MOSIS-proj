package mosis.streetsandtotems.feature_map.data.data_source

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.MapConstants
import mosis.streetsandtotems.core.domain.util.collectionSnapshotListenerCallback
import mosis.streetsandtotems.feature_map.domain.model.*
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener

class FirebaseServiceDataSource(private val db: FirebaseFirestore) {
    private val userGeoFirestore =
        GeoFirestore(db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION))

    private val totemsGeoFirestore =
        GeoFirestore(db.collection(FireStoreConstants.TOTEMS_COLLECTION))

    private val resourcesGeoFirestore =
        GeoFirestore(db.collection(FireStoreConstants.RESOURCES_COLLECTION))

    private val totemsGeoQuery = totemsGeoFirestore.queryAtLocation(
        GeoPoint(.0, .0), (MapConstants.MAXIMUM_INTERACTION_DISTANCE_IN_METERS / 1000).toDouble()
    )

    private var resourcesGeoQuery = resourcesGeoFirestore.queryAtLocation(
        GeoPoint(.0, .0), (MapConstants.MAXIMUM_INTERACTION_DISTANCE_IN_METERS / 1000).toDouble()
    )

    fun registerNotifications(onKeyEntered: () -> Unit) {
        totemsGeoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onGeoQueryError(exception: Exception) {

            }

            override fun onGeoQueryReady() {

            }

            override fun onKeyEntered(documentID: String, location: GeoPoint) {
                onKeyEntered()
            }

            override fun onKeyExited(documentID: String) {

            }

            override fun onKeyMoved(documentID: String, location: GeoPoint) {

            }
        })

        resourcesGeoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onGeoQueryError(exception: Exception) {

            }

            override fun onGeoQueryReady() {

            }

            override fun onKeyEntered(documentID: String, location: GeoPoint) {
                onKeyEntered()
            }

            override fun onKeyExited(documentID: String) {

            }

            override fun onKeyMoved(documentID: String, location: GeoPoint) {

            }
        })
    }

    fun removeNotifications() {
        totemsGeoQuery.removeAllListeners()
        resourcesGeoQuery.removeAllListeners()
    }

    fun updateNotificationQueries(center: GeoPoint) {
        totemsGeoQuery.center = center
        resourcesGeoQuery.center = center
    }

    fun updateUserLocation(user: FirebaseUser, newLocation: GeoPoint) {
        userGeoFirestore.setLocation(user.uid, newLocation)
    }

    fun registerCallbacksOnKickVoteDataUpdate(
        currentUser: FirebaseUser,
        squadId: String,
        kickVoteStartedCallback: (kickVoteData: KickVoteData) -> Unit,
        kickVoteEndedCallback: (kickVoteData: KickVoteData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.KICK_VOTE_COLLECTION)
            .whereEqualTo(FireStoreConstants.SQUAD_ID_FIELD, squadId)
            .whereNotEqualTo(FireStoreConstants.FIELD_USER_ID, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e, snapshots, kickVoteStartedCallback, null, kickVoteEndedCallback
                )
            }
    }

    fun registerCallbacksOnSquadIdInviteDataUpdate(
        currentUser: FirebaseUser, squadInviteCallback: (squadInviteData: SquadInviteData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.SQUAD_INVITES_COLLECTION)
            .whereEqualTo(FireStoreConstants.FIELD_INVITEE_ID, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e, snapshots, squadInviteCallback, null, null)
            }
    }

    fun registerCallbacksOnProfileDataUpdate(
        currentUser: FirebaseUser,
        userAddedCallback: (user: ProfileData) -> Unit,
        userModifiedCallback: (user: ProfileData) -> Unit,
        userRemovedCallback: (user: ProfileData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereNotEqualTo(FireStoreConstants.ID_FIELD, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    userAddedCallback,
                    userModifiedCallback,
                    userRemovedCallback,
                    customConversion = {
                        it.toObject<ProfileData>().copy(
                            id = it.id,
                            image_uri = it.data[FireStoreConstants.IMAGE_URI_FIELD].toString()
                        )
                    })
            }
    }


    fun registerCallbacksOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData) -> Unit,
        resourceModifiedCallback: (resource: ResourceData) -> Unit,
        resourceRemovedCallback: (resource: ResourceData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.RESOURCES_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    resourceAddedCallback,
                    resourceModifiedCallback,
                    resourceRemovedCallback,
                    customConversion = { it.toObject<ResourceData>().copy(id = it.id) })
            }
    }


    fun registerCallbacksOnTotemsUpdate(
//        currentUser: FirebaseUser,
        totemAddedCallback: (totem: TotemData) -> Unit,
        totemModifiedCallback: (totem: TotemData) -> Unit,
        totemRemovedCallback: (totem: TotemData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.TOTEMS_COLLECTION)
//            .whereEqualTo(FireStoreConstants.ID_FIELD, currentUser.uid)//fali squad id
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    totemAddedCallback,
                    totemModifiedCallback,
                    totemRemovedCallback,
                    customConversion = { it.toObject<TotemData>().copy(id = it.id) })
            }
    }


    fun registerCallbacksOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    customPinAddedCallback,
                    customPinModifiedCallback,
                    customPinRemovedCallback,
                    customConversion = { it.toObject<CustomPinData>().copy(id = it.id) })
            }
    }


    fun registerCallbacksOnHomesUpdate(
        homeAddedCallback: (home: HomeData) -> Unit,
        homeModifiedCallback: (home: HomeData) -> Unit,
        homeRemovedCallback: (home: HomeData) -> Unit,
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.HOMES_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    homeAddedCallback,
                    homeModifiedCallback,
                    homeRemovedCallback,
                    customConversion = {
                        it.toObject<HomeData>().copy(id = it.id)
                    })
            }
    }

    fun registerCallbacksOnUserInventoryUpdate(
        currentUser: FirebaseUser, userInventoryCallback: (UserInventoryData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION)
            .whereEqualTo(FireStoreConstants.ID_FIELD, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    userInventoryCallback,
                    userInventoryCallback,
                    userInventoryCallback,
                )
            }
    }


    fun registerCallbacksOnMarketUpdate(
        marketAddedCallback: (market: MarketData) -> Unit,
        marketModifiedCallback: (market: MarketData) -> Unit,
        marketRemovedCallback: (market: MarketData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.MARKET_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    marketAddedCallback,
                    marketModifiedCallback,
                    marketRemovedCallback,
                    customConversion = {
                        it.toObject<MarketData>().copy(id = it.id)
                    })
            }
    }


    fun registerCallbacksOnCurrentUserProfileDataUpdate(
        currentUser: FirebaseUser, currentUserCallback: (currentUser: ProfileData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FireStoreConstants.ID_FIELD, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(e,
                    snapshots,
                    currentUserCallback,
                    currentUserCallback,
                    currentUserCallback,
                    customConversion = {
                        it.toObject<ProfileData>().copy(
                            id = it.id,
                            image_uri = it.data[FireStoreConstants.IMAGE_URI_FIELD].toString()
                        )
                    })
            }
    }

}