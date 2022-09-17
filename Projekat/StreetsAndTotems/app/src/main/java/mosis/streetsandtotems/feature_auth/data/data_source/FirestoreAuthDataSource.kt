package mosis.streetsandtotems.feature_auth.data.data_source

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.BackpackConstants
import mosis.streetsandtotems.core.FireStoreConstants
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

    suspend fun addUser(profileData: ProfileData) {
        if (profileData.id != null && profileData.user_name != null) {
            db.runBatch {
                it.set(
                    db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
                        .document(profileData.id),
                    profileData
                )

                it.set(
                    db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION)
                        .document(profileData.id),
                    UserInventoryData(
                        empty_spaces = BackpackConstants.DEFAULT_SIZE,
                        inventory = InventoryData(
                            0,
                            0,
                            0,
                            0,
                            BackpackConstants.DEFAULT_TOTEM_COUNT
                        )
                    )
                )

                it.set(
                    db.collection(FireStoreConstants.LEADERBOARD_COLLECTION).document(
                        profileData.id
                    ),
                    LeaderboardUserData(profileData.user_name, 0)
                )
            }.await()
        }
    }
}