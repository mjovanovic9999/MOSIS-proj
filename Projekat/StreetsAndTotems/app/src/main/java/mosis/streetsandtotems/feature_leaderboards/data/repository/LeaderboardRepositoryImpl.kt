package mosis.streetsandtotems.feature_leaderboards.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_leaderboards.data.data_source.LeaderboardDataSource
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUserData
import mosis.streetsandtotems.feature_leaderboards.domain.repository.LeaderboardRepository
import mosis.streetsandtotems.feature_map.domain.model.ProfileData

class LeaderboardRepositoryImpl(
    private val auth: FirebaseAuth, private val leaderboardDataSource: LeaderboardDataSource
) : LeaderboardRepository {
    private var onLeaderboardChangeListenerRegistration: ListenerRegistration? = null

    override fun registerCallbacksOnLeaderboardUpdate(
        leaderboardUserAddedCallback: (user: LeaderboardUserData) -> Unit,
        leaderboardUserModifiedCallback: (user: LeaderboardUserData) -> Unit,
        leaderboardUserRemovedCallback: (user: LeaderboardUserData) -> Unit
    ) {
        auth.currentUser?.let {
            onLeaderboardChangeListenerRegistration =
                leaderboardDataSource.registerCallbacksOnLeaderboardDataUpdate(
                    leaderboardUserAddedCallback,
                    leaderboardUserModifiedCallback,
                    leaderboardUserRemovedCallback
                )
        }
    }

    override fun removeCallbacksOnLeaderboardUpdate() {
        onLeaderboardChangeListenerRegistration?.let {
            it.remove()
            onLeaderboardChangeListenerRegistration = null
        }
    }

    override suspend fun getUserData(username: String): Flow<Response<ProfileData>> = flow {
        try {
            emit(Response.Loading)
            val userSnapshot = leaderboardDataSource.getUserData(username).await()
            val user = userSnapshot.toObjects(ProfileData::class.java).firstOrNull()
            if (user != null) emit(Response.Success(user.copy(id = userSnapshot.documents.firstOrNull()?.id)))
            else emit(Response.Error(message = MessageConstants.USER_NOT_FOUND))
        } catch (e: Exception) {
            emit(Response.Error(MessageConstants.DEFAULT_ERROR_MESSAGE))
        }
    }
}