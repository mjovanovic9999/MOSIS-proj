package mosis.streetsandtotems.feature_map.data.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.data.data_source.PreferencesDataStore
import mosis.streetsandtotems.feature_map.data.data_source.FirebaseMapDataSource
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.search_results.PlayerSearchResultItem
import mosis.streetsandtotems.feature_map.presentation.components.search_results.ResourceSearchResultItem

class MapViewModelRepositoryImpl(
    private val preferenceDataSource: PreferencesDataStore,
    private val firebaseMapDataSource: FirebaseMapDataSource,
) : MapViewModelRepository {
    override suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        firebaseMapDataSource.addCustomPin(l, visible_to, placed_by, text)
    }

    override suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        firebaseMapDataSource.updateCustomPin(id, visible_to, placed_by, text)
    }

    override suspend fun deleteCustomPin(id: String) {
        firebaseMapDataSource.deleteCustomPin(id)
    }

    override suspend fun addHome(l: GeoPoint) {
        val squadId = preferenceDataSource.getUserSquadId()
        if (squadId == "")
            firebaseMapDataSource.addHome(preferenceDataSource.getUserId(), l)
        else
            firebaseMapDataSource.addHome(squadId, l)

    }

    override suspend fun deleteHome() {
        firebaseMapDataSource.deleteHome(preferenceDataSource.getUserId())
    }

    override suspend fun updateResource(resourceId: String, newCount: Int) {
        if (newCount <= 0) firebaseMapDataSource.deleteResource(resourceId)
        else firebaseMapDataSource.updateResource(resourceId, newCount)
    }

    override suspend fun updateUserInventory(newUserInventoryData: UserInventoryData) {
        firebaseMapDataSource.updateUserInventory(
            preferenceDataSource.getUserId(), newUserInventoryData
        )
    }

    override suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        firebaseMapDataSource.updateMarket(newMarket)
    }

    override suspend fun updateHomeInventory(homeId: String, newInventoryData: InventoryData) {
        firebaseMapDataSource.updateHome(homeId, newInventoryData)
    }

    override suspend fun updateHomeLocation(homeId: String, l: GeoPoint) {
        firebaseMapDataSource.updateHome(homeId, l = l)
    }


    override suspend fun updateTotem(totemId: String, newTotem: TotemData) {
        firebaseMapDataSource.updateTotem(totemId, newTotem)
    }

    override suspend fun deleteTotem(totemId: String) {
        firebaseMapDataSource.deleteTotem(totemId)
    }

    override suspend fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel): RiddleData? {
        return firebaseMapDataSource.getRiddle(protectionLevel)
    }

    override suspend fun updateLeaderboard(userId: String, addLeaderboardPoints: Int) {
        return firebaseMapDataSource.updateLeaderboard(userId, addLeaderboardPoints)
    }

    override suspend fun updateSquadLeaderboard(squadId: String, addSquadLeaderboardPoints: Int) {
        return firebaseMapDataSource.updateSquadLeaderboard(squadId, addSquadLeaderboardPoints)
    }

    override suspend fun initInviteToSquad(inviteeId: String) {
        return firebaseMapDataSource.initInviteToSquad(preferenceDataSource.getUserId(), inviteeId)
    }

    override suspend fun acceptInviteToSquad(inviterId: String) {
        return firebaseMapDataSource.acceptInviteToSquad(
            inviterId, preferenceDataSource.getUserId()
        )
    }

    override suspend fun declineInviteToSquad(inviterId: String) {
        firebaseMapDataSource.declineInviteToSquad(
            inviterId, preferenceDataSource.getUserId()
        )
    }

    override suspend fun isSquadFull(squadId: String): Boolean =
        firebaseMapDataSource.isSquadFull(squadId)

    override suspend fun isUserInSquad(inviteeId: String): Boolean =
        firebaseMapDataSource.isUserInSquad(inviteeId)

    override suspend fun initKickVote(user_id: String) {
        firebaseMapDataSource.initKickVote(
            squad_id = preferenceDataSource.getUserSquadId(),
            user_id = user_id,
            myId = preferenceDataSource.getUserId(),
        )
    }

    override suspend fun kickVote(userId: String, myVote: Vote) {
        firebaseMapDataSource.kickVote(
            preferenceDataSource.getUserId(), preferenceDataSource.getUserSquadId(), userId, myVote
        )
    }

    override suspend fun searchUsersInRadius(
        username: String,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<PlayerSearchResultItem>) -> Unit,
        onSearchFailed: () -> Unit,
        onResultItemClick: (ProfileData) -> Unit
    ) {
        val userId = preferenceDataSource.getUserId()
        val users = mutableListOf<PlayerSearchResultItem>()
        firebaseMapDataSource.searchUsersInRadius(userLocation, radius, onSearchCompleteCallback = {
            it?.forEach() { userSnapshot ->
                val user =
                    userSnapshot.toObject(ProfileData::class.java)?.copy(id = userSnapshot.id)
                if (user != null && user.user_name?.contains(
                        username,
                        ignoreCase = true
                    ) == true && user.id != userId && user.is_online == true
                ) users.add(
                    PlayerSearchResultItem(user, onResultItemClick)
                )
            }
            onSearchCompleted(users)
        }, onSearchFailedCallback = onSearchFailed)
    }

    override fun searchResourceInRadius(
        type: ResourceType,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<ResourceSearchResultItem>) -> Unit,
        onSearchFailed: () -> Unit,
        onResultItemClick: (ResourceData) -> Unit,
    ) {
        val resources = mutableListOf<ResourceSearchResultItem>()
        firebaseMapDataSource.searchResourcesInRadius(userLocation, radius, {
            it?.forEach { resourceSnapshot ->
                val resource = resourceSnapshot.toObject(ResourceData::class.java)
                    ?.copy(id = resourceSnapshot.id)
                if (resource != null && resource.type == type) resources.add(
                    ResourceSearchResultItem(resource, onResultItemClick, userLocation)
                )
            }
            onSearchCompleted(resources)
        }, onSearchFailed)
    }
}


//    override suspend fun registerCallbackOnUserInventoryUpdate(userId: String): UserInventoryData? {
//        return try {
//            firebaseMapDataSource.getUserInventory(userId)
//        } catch (e: Exception) {
//            Log.d("tag", e.message.toString())
//            null
//        }
//    }

