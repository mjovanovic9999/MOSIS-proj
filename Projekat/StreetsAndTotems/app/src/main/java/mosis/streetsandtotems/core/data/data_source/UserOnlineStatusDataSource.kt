package mosis.streetsandtotems.core.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import mosis.streetsandtotems.core.FirestoreConstants

class UserOnlineStatusDataSource(private val db: FirebaseFirestore) {
    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId)
            .update(FirestoreConstants.IS_ONLINE_FIELD, isOnline)
    }
}