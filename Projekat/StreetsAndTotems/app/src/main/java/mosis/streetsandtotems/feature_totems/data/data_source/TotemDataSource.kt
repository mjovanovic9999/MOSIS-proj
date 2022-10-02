package mosis.streetsandtotems.feature_totems.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.domain.util.collectionSnapshotListenerCallback
import mosis.streetsandtotems.feature_map.domain.model.TotemData

class TotemDataSource(private val db: FirebaseFirestore) {
    fun getUsername(id: String): Task<QuerySnapshot> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FireStoreConstants.ID_FIELD, id).get()
    }

    fun registerCallbacksOnTotemDataUpdate(
        totemAdded: (TotemData) -> Unit,
        totemModified: (TotemData) -> Unit,
        totemDeleted: (TotemData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.TOTEMS_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e, snapshots, totemAdded, totemModified, totemDeleted
                ) { it.toObject(TotemData::class.java).copy(id = it.id) }
            }
    }
}