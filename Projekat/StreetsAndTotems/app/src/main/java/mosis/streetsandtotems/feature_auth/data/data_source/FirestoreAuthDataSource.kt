package mosis.streetsandtotems.feature_auth.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import mosis.streetsandtotems.core.BackpackConstants
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFields
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

class FirestoreAuthDataSource(private val db: FirebaseFirestore) {
    fun updateUserProfile(userId: String, profileFields: ProfileFields): Task<Void> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId).update(
            mapOf(
                FireStoreConstants.USER_NAME_FIELD to profileFields.userName,
                FireStoreConstants.FIRST_NAME_FIELD to profileFields.firstName,
                FireStoreConstants.LAST_NAME_FIELD to profileFields.lastName,
                FireStoreConstants.EMAIL_FIELD to profileFields.email,
                FireStoreConstants.PHONE_NUMBER_FIELD to profileFields.phoneNumber,
                FireStoreConstants.IMAGE_URI_FIELD to profileFields.imagePath
            )
        )
    }

    fun getUsersWithEmail(email: String): Task<QuerySnapshot> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FireStoreConstants.EMAIL_FIELD, email).get()
    }

    fun getUsersWithUsername(username: String): Task<QuerySnapshot> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FireStoreConstants.USER_NAME_FIELD, username).get()
    }

    fun removeUser(userId: String): Task<Unit> {
        return db.runTransaction {
            val userSnapshot =
                it.get(db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId))

            if (userSnapshot.exists()) {
                val userInventorySnapshot = it.get(
                    db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(userId)
                )

                val leaderboardSnapshot = it.get(
                    db.collection(FireStoreConstants.LEADERBOARD_COLLECTION).document(userId)
                )

                it.delete(
                    db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId)
                )

                if (userInventorySnapshot.exists()) it.delete(
                    db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(userId)
                )

                if (leaderboardSnapshot.exists()) it.delete(
                    db.collection(FireStoreConstants.LEADERBOARD_COLLECTION).document(userId)
                )
            }
        }
    }

    fun addUser(profileData: ProfileData): Task<Void>? {
        if (profileData.id != null && profileData.user_name != null) {
            return db.runBatch {
                it.set(
                    db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
                        .document(profileData.id), profileData
                )

                it.set(
                    db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION)
                        .document(profileData.id), UserInventoryData(
                        empty_spaces = BackpackConstants.DEFAULT_SIZE, inventory = InventoryData(
                            0, 0, 0, 0, BackpackConstants.DEFAULT_TOTEM_COUNT
                        )
                    )
                )

                it.set(
                    db.collection(FireStoreConstants.LEADERBOARD_COLLECTION).document(
                        profileData.id
                    ), LeaderboardUserData(profileData.user_name, 0)
                )
            }
        }
        return null
    }


}