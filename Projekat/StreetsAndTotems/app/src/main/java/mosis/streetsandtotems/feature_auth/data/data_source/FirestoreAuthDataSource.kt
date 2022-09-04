package mosis.streetsandtotems.feature_auth.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_map.domain.model.ProfileData

class FirestoreAuthDataSource(private val db: FirebaseFirestore) {
//    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
//        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId)
//            .update(FirestoreConstants.IS_ONLINE_FIELD, isOnline)
//    }
//
//    suspend fun getUserData(userId: String): UserData? {
//        val userSnapshot =
//            db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId).get()
//                .await()
//
//        return userSnapshot.toObject(UserData::class.java)?.copy(
//            id = userId,
//            image = Uri.parse(userSnapshot.getField(FirestoreConstants.IMAGE_URI_FIELD))
//        )
//    }

    suspend fun addUser(profileData: ProfileData) {
        profileData.id?.let {
            db.runBatch {
                db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(profileData.id)
                    .set(profileData)

//                db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION).document(profileData.id)
//                    .set()

              //  db.collection(FirestoreConstants.LEADERBOARD_COLLECTION).document(profileData.id)
            }.await()
        }
    }
}