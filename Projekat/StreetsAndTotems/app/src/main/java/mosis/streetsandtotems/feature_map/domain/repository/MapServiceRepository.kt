package mosis.streetsandtotems.feature_map.domain.repository

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*

interface MapServiceRepository {
    suspend fun updateMyLocation(newLocation: GeoPoint)

    fun removeCallbackOnKickVoteDataUpdate()

    fun removeCallbackOnSquadInviteDataUpdate()

    suspend fun registerCallbackOnKickVoteDataUpdate(
        kickVoteStartedCallback: (kickVote: KickVoteData) -> Unit,
        kickVoteEndedCallback: (kickVote: KickVoteData) -> Unit
    )

    suspend fun registerCallbackOnSquadInviteDataUpdate(
        squadInviteCallback: (squadInvite: SquadInviteData) -> Unit
    )

    fun registerCallbackOnCurrentUserProfileDataUpdate(
        currentUserCallback: (currentUser: ProfileData) -> Unit
    )

    fun registerCallbacksOnProfileDataUpdate(
        userAddedCallback: (user: ProfileData) -> Unit,
        userModifiedCallback: (user: ProfileData) -> Unit,
        userRemovedCallback: (user: ProfileData) -> Unit
    )

    fun registerCallbackOnResourcesUpdate(
        resourceAddedCallback: (resource: ResourceData) -> Unit,
        resourceModifiedCallback: (resource: ResourceData) -> Unit,
        resourceRemovedCallback: (resource: ResourceData) -> Unit,
    )

    fun registerCallbackOnTotemsUpdate(
        totemAddedCallback: (totem: TotemData) -> Unit,
        totemModifiedCallback: (totem: TotemData) -> Unit,
        totemRemovedCallback: (totem: TotemData) -> Unit,
    )

    fun registerCallbackOnCustomPinsUpdate(
        customPinAddedCallback: (customPin: CustomPinData) -> Unit,
        customPinModifiedCallback: (customPin: CustomPinData) -> Unit,
        customPinRemovedCallback: (customPin: CustomPinData) -> Unit,
    )

    fun registerCallbackOnHomesUpdate(
        homeAddedCallback: (home: HomeData) -> Unit,
        homeModifiedCallback: (home: HomeData) -> Unit,
        homeRemovedCallback: (home: HomeData) -> Unit,
    )

    fun registerCallbackOnUserInventoryUpdate(
        userInventoryCallback: (userInventoryData: UserInventoryData) -> Unit
    )

    fun registerCallbackOnMarketUpdate(
        marketAddedCallback: (market: MarketData) -> Unit,
        marketModifiedCallback: (market: MarketData) -> Unit,
        marketRemovedCallback: (market: MarketData) -> Unit,
    )

    fun registerNotifications(onKeyEntered: () -> Unit)

    fun removeNotifications()

    fun updateNotificationQueries(center: GeoPoint)

    fun removeAllCallbacks()


}