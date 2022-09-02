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
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData
import org.imperiumlabs.geofirestore.GeoFirestore

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    private val userGeoFirestore =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    private val userGeoQuery =
        GeoFirestore(db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION))

    fun updateUserLocation(user: FirebaseUser, newLocation: GeoPoint) {
        userGeoFirestore.setLocation(user.uid, newLocation)
    }

    suspend fun getUserInGameData(currentUser: FirebaseUser): Flow<UserInGameData?> {
        return db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
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
            .asFlow()
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


    suspend fun getResources(): Flow<ResourceData?> {
        return db.collection(FirestoreConstants.RESOURCES_COLLECTION).get()
            .await().documents.map {
                it.toObject<ResourceData>()?.copy(id = it.id)
            }.asFlow()
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


    suspend fun getTotems(): Flow<TotemData?> {
        return db.collection(FirestoreConstants.TOTEMS_COLLECTION).get()
            .await().documents.map {
                it.toObject<TotemData>()?.copy(id = it.id)
            }.asFlow()
    }

    fun registerCallbacksOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData?) -> Unit,
        totemModifiedCallback: (totem: TotemData?) -> Unit,
        totemRemovedCallback: (totem: TotemData?) -> Unit
    ): ListenerRegistration {
        return db.collection(FirestoreConstants.TOTEMS_COLLECTION)
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