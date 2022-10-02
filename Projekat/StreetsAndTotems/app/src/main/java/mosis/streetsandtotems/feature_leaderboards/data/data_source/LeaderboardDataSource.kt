package mosis.streetsandtotems.feature_leaderboards.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.domain.util.collectionSnapshotListenerCallback
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData

class LeaderboardDataSource(private val db: FirebaseFirestore) {
    fun registerCallbacksOnLeaderboardDataUpdate(
        leaderboardUserAddedCallback: (LeaderboardUserData) -> Unit,
        leaderboardUserModifiedCallback: (LeaderboardUserData) -> Unit,
        leaderboardUserDeletedCallback: (LeaderboardUserData) -> Unit
    ): ListenerRegistration {
        return db.collection(FireStoreConstants.LEADERBOARD_COLLECTION)
            .addSnapshotListener { snapshots, e ->
                collectionSnapshotListenerCallback(
                    e,
                    snapshots,
                    leaderboardUserAddedCallback,
                    leaderboardUserModifiedCallback,
                    leaderboardUserDeletedCallback
                )
            }
    }

    fun getUserData(username: String): Task<QuerySnapshot> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION)
            .whereEqualTo(FireStoreConstants.USER_NAME_FIELD, username).get()
    }
}