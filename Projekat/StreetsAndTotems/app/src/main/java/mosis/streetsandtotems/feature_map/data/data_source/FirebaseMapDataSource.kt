package mosis.streetsandtotems.feature_map.data.data_source

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_map.domain.model.*
import org.imperiumlabs.geofirestore.GeoFirestore
import java.util.*

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    private val userGeoFirestore =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    private val userGeoQuery =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    fun updateUserLocation(user: FirebaseUser, newLocation: GeoPoint) {
        userGeoFirestore.setLocation(user.uid, newLocation)
    }

    fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).add(
            mapOf(
                "l" to l,
                "text" to text,
                "placed_by" to placed_by,
                "visible_to" to visible_to,
            )
        )
    }

    fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (visible_to != null) data["placed_by"] = placed_by as Any
        if (placed_by != null) data["placed_by"] = placed_by as Any
        if (text != null) data["text"] = text as Any

        if (data.isNotEmpty())
            db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).document(id)
                .update(data)
    }

    fun deleteCustomPin(id: String) {
        db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete()
    }

    fun addHome(myId: String, l: GeoPoint) {
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).set(
            mapOf(
                "l" to l,
                "inventory" to mapOf(
                    "wood" to 0,
                    "brick" to 0,
                    "stone" to 0,
                    "emerald" to 0,
                    "totem" to 1,
                ),
            )
        )
    }

    fun updateHome() {}

    fun deleteHome(myId: String) {//nestovano se drugacije brise?????????
        db.collection(FirestoreConstants.HOMES_COLLECTION).document(myId).delete()

    }


    fun registerCallbacksOnUserInGameDataUpdate(
        currentUser: FirebaseUser,
        userAddedCallback: (user: UserInGameData?) -> Unit,
        userModifiedCallback: (user: UserInGameData?) -> Unit,
        userRemovedCallback: (user: UserInGameData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
            .whereNotEqualTo(FirestoreConstants.ID_FIELD, currentUser.uid)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    userAddedCallback,
                    userModifiedCallback,
                    userRemovedCallback,
                    customConversion = { it.toObject<UserInGameData>().copy(id = it.id) }
                )
            }
    }


    fun registerCallbacksOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData?) -> Unit,
        resourceModifiedCallback: (resource: ResourceData?) -> Unit,
        resourceRemovedCallback: (resource: ResourceData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.RESOURCES_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    resourceAddedCallback,
                    resourceModifiedCallback,
                    resourceRemovedCallback,
                    customConversion = { it.toObject<ResourceData>().copy(id = it.id) }
                )
            }
    }


    fun registerCallbacksOnTotemsUpdate(
//        currentUser: FirebaseUser,
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.TOTEMS_COLLECTION)
//            .whereEqualTo(FirestoreConstants.ID_FIELD, currentUser.uid)//fali squad id
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    totemAddedCallback,
                    totemModifiedCallback,
                    totemRemovedCallback,
                    customConversion = { it.toObject<TotemData>().copy(id = it.id) }
                )
            }
    }


    fun registerCallbacksOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData?) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData?) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.CUSTOM_PINS_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    customPinAddedCallback,
                    customPinModifiedCallback,
                    customPinRemovedCallback,
                    customConversion = { it.toObject<CustomPinData>().copy(id = it.id) }
                )
            }
    }


    fun registerCallbacksOnHomesUpdate(
        homeAddedCallback: (home: HomeData?) -> Unit,
        homeModifiedCallback: (home: HomeData?) -> Unit,
        homeRemovedCallback: (home: HomeData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.HOMES_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    homeAddedCallback,
                    homeModifiedCallback,
                    homeRemovedCallback,
                    customConversion = {
/////////////////////////////check point ne znam sta treba
//                        it.reference.collection("inventory").get().addOnCompleteListener { ness ->
//                            Log.d(
//                                "tag", "VRATIO MI" + ness.result.toString()
//                            )

                        it.toObject<HomeData>()
                            .copy(
                                id = it.id,
//                                emerald = (it.data.inventory as Map<*, *>).get("emerald") as Int?
                            )


                    }
                )
            }
    }

/*
db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
            .whereNotEqualTo(FirestoreConstants.ID_FIELD, currentUser.uid).get()
            .await().documents.map {
                val profileDataSnapshot =
                    it.getField<DocumentReference>("profile_data")?.get()?.await()
                val profileData = profileDataSnapshot?.toObject<ProfileData>()
                    ?.copy(image = Uri.parse(profileDataSnapshot.getField<String>("image_uri")))
                Log.d("tag", profileData.toString())
                it.toObject<UserInGameData>()?.copy(
                    id = it.id,
                    display_data = profileData
                )
            }
*/

    private inline fun <reified T> collectionSnapshotListenerCallback(
        e: FirebaseFirestoreException?,
        snapshots: QuerySnapshot?,
        userAddedCallback: (user: T?) -> Unit,
        userModifiedCallback: (user: T?) -> Unit,
        userRemovedCallback: (user: T?) -> Unit,
        customConversion: (document: QueryDocumentSnapshot) -> T? = { it.toObject<T>() }
    ) {
        if (e != null) {
            Log.w("tag", "listen:error", e)
            return
        }

        for (dc in snapshots!!.documentChanges) {
            when (dc.type) {
                DocumentChange.Type.ADDED -> userAddedCallback(customConversion(dc.document))
                DocumentChange.Type.MODIFIED -> userModifiedCallback(customConversion(dc.document))
                DocumentChange.Type.REMOVED -> userRemovedCallback(customConversion(dc.document))
            }
        }
    }
}

