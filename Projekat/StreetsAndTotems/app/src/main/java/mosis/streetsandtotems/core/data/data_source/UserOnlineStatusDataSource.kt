package mosis.streetsandtotems.core.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import mosis.streetsandtotems.core.FireStoreConstants

class UserOnlineStatusDataSource(private val db: FirebaseFirestore) {
    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId)
            .update(FireStoreConstants.IS_ONLINE_FIELD, isOnline)
    }
}