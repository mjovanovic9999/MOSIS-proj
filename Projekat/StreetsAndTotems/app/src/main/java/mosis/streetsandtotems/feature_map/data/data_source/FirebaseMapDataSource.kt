package mosis.streetsandtotems.feature_map.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_map.domain.model.Location

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    fun updateUserLocation(user: FirebaseUser, newLocation: Location): Task<Void> {
        return db.collection(FirestoreConstants.USER_IN_GAME_DATA_COLLECTION)
            .document(user.uid)
            .update(FirestoreConstants.LOCATION_DOCUMENT_FIELD, newLocation)
    }

}