package mosis.streetsandtotems.feature_auth.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import mosis.streetsandtotems.core.BackpackConstants
import mosis.streetsandtotems.core.FirestoreConstants
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

class FirestoreAuthDataSource(private val db: FirebaseFirestore) {
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
    fun getUsersWithEmail(email: String): Task<QuerySnapshot> {
        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FirestoreConstants.EMAIL_FIELD, email).get()
    }

    fun getUsersWithUsername(username: String): Task<QuerySnapshot> {
        return db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FirestoreConstants.USER_NAME_FIELD, username).get()
    }

    fun removeUser(userId: String): Task<Unit> {
        return db.runTransaction {
            val userSnapshot =
                it.get(db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId))

            if (userSnapshot.exists()) {
                val userInventorySnapshot = it.get(
                    db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION).document(userId)
                )

                val leaderboardSnapshot = it.get(
                    db.collection(FirestoreConstants.LEADERBOARD_COLLECTION).document(userId)
                )

                it.delete(
                    db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION).document(userId)
                )

                if (userInventorySnapshot.exists()) it.delete(
                    db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION).document(userId)
                )

                if (leaderboardSnapshot.exists()) it.delete(
                    db.collection(FirestoreConstants.LEADERBOARD_COLLECTION).document(userId)
                )
            }
        }
    }

    fun addUser(profileData: ProfileData): Task<Void>? {
        if (profileData.id != null && profileData.user_name != null) {
            return db.runBatch {
                it.set(
                    db.collection(FirestoreConstants.PROFILE_DATA_COLLECTION)
                        .document(profileData.id), profileData
                )

                it.set(
                    db.collection(FirestoreConstants.USER_INVENTORY_COLLECTION)
                        .document(profileData.id), UserInventoryData(
                        empty_spaces = BackpackConstants.DEFAULT_SIZE, inventory = InventoryData(
                            0, 0, 0, 0, BackpackConstants.DEFAULT_TOTEM_COUNT
                        )
                    )
                )

                it.set(
                    db.collection(FirestoreConstants.LEADERBOARD_COLLECTION).document(
                        profileData.id
                    ), LeaderboardUserData(profileData.user_name, 0)
                )
            }
        }
        return null
    }


}