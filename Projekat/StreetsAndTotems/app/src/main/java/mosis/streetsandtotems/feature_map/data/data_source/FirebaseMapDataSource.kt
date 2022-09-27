package mosis.streetsandtotems.feature_map.data.data_source

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.FireStoreConstants.CUSTOM_PINS_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.EASY_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_BRICK
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_EMERALD
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVENTORY
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVITEE_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVITER_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_PLACED_BY
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_SQUAD_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_STONE
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_TOTEM
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_USERS
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_USER_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_VISIBLE_TO
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_WOOD
import mosis.streetsandtotems.core.FireStoreConstants.HARD_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.HOMES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ITEM_COUNT
import mosis.streetsandtotems.core.FireStoreConstants.KICK_VOTE_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.L
import mosis.streetsandtotems.core.FireStoreConstants.LEADERBOARD_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.MARKET_DOCUMENT_ID
import mosis.streetsandtotems.core.FireStoreConstants.MEDIUM_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ORDER_NUMBER
import mosis.streetsandtotems.core.FireStoreConstants.PROFILE_DATA_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.RIDDLE_COUNT_VALUE
import mosis.streetsandtotems.core.FireStoreConstants.SQUADS_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.SQUAD_INVITES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.TOTEMS_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.USER_NAME_FIELD
import mosis.streetsandtotems.core.PointsConversion.MAX_SQUAD_MEMBERS_COUNT
import mosis.streetsandtotems.core.PointsConversion.SQUAD_MEMBERS_POINTS_COEFFICIENT
import mosis.streetsandtotems.feature_map.domain.model.*
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.extension.getAtLocation
import kotlin.random.Random

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        try {
            val playerName =
                db.collection(PROFILE_DATA_COLLECTION).document(placed_by).get().await()
                    .getString(USER_NAME_FIELD)

            db.collection(CUSTOM_PINS_COLLECTION).add(
                mapOf(
                    L to l,
                    "text" to text,
                    FIELD_PLACED_BY to placed_by,
                    FIELD_VISIBLE_TO to visible_to,
                    "player_name" to playerName,
                )
            ).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        try {
            val data: MutableMap<String, Any> = mutableMapOf()
            if (visible_to != null) data[FIELD_PLACED_BY] = placed_by as Any
            if (placed_by != null) {
                data[FIELD_PLACED_BY] = placed_by as Any
                data["player_name"] =
                    db.collection(PROFILE_DATA_COLLECTION).document(placed_by).get().await()
                        .getString(USER_NAME_FIELD) as Any
            }
            if (text != null) data["text"] = text as Any

            if (data.isNotEmpty()) db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION)
                .document(id)
                .update(data).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun deleteCustomPin(id: String) {
        try {
            db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete().await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun addHome(myId: String, l: GeoPoint?) {
        try {
            db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).set(
                mapOf(
                    L to l,
                    FIELD_INVENTORY to mapOf(
                        FIELD_WOOD to 0,
                        FIELD_BRICK to 0,
                        FIELD_EMERALD to 0,
                        FIELD_STONE to 0,
                        FIELD_TOTEM to 0,
                    ),
                )
            ).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun updateHome(
        homeId: String, newInventoryData: InventoryData? = null, l: GeoPoint? = null
    ) {
        try {
            val data: MutableMap<String, Any> = mutableMapOf()
            if (newInventoryData != null) data[FIELD_INVENTORY] = newInventoryData
            if (l != null) data[L] = l

            if (data.isNotEmpty()) db.collection(FireStoreConstants.HOMES_COLLECTION)
                .document(homeId)
                .update(data).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun deleteHome(myId: String) {
        try {
            db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).delete().await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    fun deleteHomeTransaction(transaction: Transaction, myId: String) {
        try {
            transaction.delete(
                db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId)
            )
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun updateResource(resourceId: String, remaining: Int) {
        try {
            db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).update(
                mapOf("remaining" to remaining)
            ).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun deleteResource(resourceId: String) {
        try {
            db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).delete()
                .await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun getUserInventory(userId: String): UserInventoryData? {
        return try {
            db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(userId)
                .get()
                .await().toObject(UserInventoryData::class.java)
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            null
        }
    }

    suspend fun updateUserInventory(
        myId: String, newUserInventoryData: UserInventoryData
    ) {
        try {
            db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(myId)
                .set(newUserInventoryData).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void>? {//////////dodat ?
        return try {
            db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId)
                .update(FireStoreConstants.IS_ONLINE_FIELD, isOnline)
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            null
        }
    }

    suspend fun getUserData(userId: String): UserData? {
        return try {
            db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId).get()
                .await().toObject(UserData::class.java)
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            null
        }
    }

    suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        try {
            db.collection(FireStoreConstants.MARKET_COLLECTION).document(MARKET_DOCUMENT_ID)
                .update(mapOf("items" to newMarket)).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun updateTotem(totemId: String, newTotem: TotemData) {
        try {
            db.collection(TOTEMS_COLLECTION).document(totemId).set(newTotem).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun deleteTotem(totemId: String) {
        try {
            db.collection(TOTEMS_COLLECTION).document(totemId).delete().await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel): RiddleData? {
        try {
            val collection = when (protectionLevel) {
                ProtectionLevel.RiddleProtectionLevel.Low -> EASY_RIDDLES_COLLECTION
                ProtectionLevel.RiddleProtectionLevel.Medium -> MEDIUM_RIDDLES_COLLECTION
                ProtectionLevel.RiddleProtectionLevel.High -> HARD_RIDDLES_COLLECTION
            }

            val count =
                db.collection(collection).document(ITEM_COUNT).get().await()
                    .getLong(RIDDLE_COUNT_VALUE)
            val riddles = db.collection(collection).whereEqualTo(
                ORDER_NUMBER, Random.nextInt(0, count?.toInt() ?: 10)
            ).get().await().toObjects(RiddleData::class.java)

            return riddles[0]
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            return null
        }
    }

    suspend fun updateLeaderboard(userId: String, addLeaderboardPoints: Int) {
        try {
            db.runTransaction {
                val docRef = db.collection(LEADERBOARD_COLLECTION).document(userId)
                val points = it.get(docRef).getLong("points")
                it.update(docRef, "points", (points ?: 0) + addLeaderboardPoints)
            }.await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun updateSquadLeaderboard(squadId: String, addSquadLeaderboardPoints: Int) {
        try {
            val ids = db.collection(SQUADS_COLLECTION).document(squadId)
                .get()
                .await().get(FIELD_USERS) as List<*>
            for (item in ids) {
                if (item is String) updateLeaderboard(
                    item, (addSquadLeaderboardPoints * SQUAD_MEMBERS_POINTS_COEFFICIENT).toInt()
                )
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    //region squad interaction

    private suspend fun onAcceptInviteMergeHouse(myId: String, joinedSquadId: String) {
        db.runTransaction { transaction ->
            val homesCollectionRef = db.collection(HOMES_COLLECTION)

            val myOldHomeDocRef = homesCollectionRef.document(myId)
            val myOldHome = transaction.get(myOldHomeDocRef).toObject(HomeData::class.java)

            transaction.get(homesCollectionRef.document(joinedSquadId))
                .toObject(HomeData::class.java)?.let { squadHome ->
                    homesCollectionRef.document(joinedSquadId).set(
                        squadHome.copy(
                            inventory = squadHome.inventory?.copy(
                                emerald = (squadHome.inventory.emerald
                                    ?: 0) + (myOldHome?.inventory?.emerald
                                    ?: 0),
                                stone = (squadHome.inventory.stone
                                    ?: 0) + (myOldHome?.inventory?.stone
                                    ?: 0),
                                brick = (squadHome.inventory.brick
                                    ?: 0) + (myOldHome?.inventory?.brick
                                    ?: 0),
                                wood = (squadHome.inventory.wood
                                    ?: 0) + (myOldHome?.inventory?.wood
                                    ?: 0),
                                totem = (squadHome.inventory.totem
                                    ?: 0) + (myOldHome?.inventory?.totem
                                    ?: 0)
                            )
                        )
                    )
                    transaction.delete(myOldHomeDocRef)
                }
        }.await()
    }

    private fun addUserToSquadAndUpdateUser(
        transaction: Transaction, squadId: String, inviteeId: String
    ) {
        try {
            val docRef = db.collection(SQUADS_COLLECTION).document(squadId)

            val list = mutableListOf<String>()
            for (item in transaction.get(docRef).get(FIELD_USERS) as List<*>) {
                if (item is String) list.add(item)
            }
            if (list.isNotEmpty()) {
                list.add(inviteeId)
                transaction.update(docRef, FIELD_USERS, list)
            }

            transaction.update(
                db.collection(PROFILE_DATA_COLLECTION).document(inviteeId), FIELD_SQUAD_ID, squadId
            )
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private fun createSquadAndReturnSquadId(
        transaction: Transaction,
        inviterId: String,
        inviteeId: String
    ): String? {
        try {
            val newSquadId = db.collection(SQUADS_COLLECTION).document().id
            transaction.set(
                db.collection(SQUADS_COLLECTION).document(newSquadId),
                mapOf(FIELD_USERS to listOf(inviterId, inviteeId))
            )

            transaction.update(
                db.collection(PROFILE_DATA_COLLECTION).document(inviterId),
                FIELD_SQUAD_ID,
                newSquadId
            )
            transaction.update(
                db.collection(PROFILE_DATA_COLLECTION).document(inviteeId),
                FIELD_SQUAD_ID,
                newSquadId
            )
            return newSquadId
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            return null
        }
    }

    suspend fun initInviteToSquad(inviterId: String, inviteeId: String) {
        try {
            if (getInviteIdOrNull(inviterId, inviteeId) == null)
                db.collection(SQUAD_INVITES_COLLECTION).document().set(
                    mapOf(
                        FIELD_INVITER_ID to inviterId,
                        FIELD_INVITEE_ID to inviteeId,
                    )
                ).await()
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun isUserInSquad(inviteeId: String): Boolean = try {
        db.collection(PROFILE_DATA_COLLECTION).document(inviteeId).get().await()
            .getString(FIELD_SQUAD_ID).let {
                !(it == null || it == "")
            }
    } catch (e: Exception) {
        Log.d("tag", e.message.toString())
        true
    }


    private suspend fun getSquadUserIds(squadId: String): List<String> {
        return try {
            val list = mutableListOf<String>()
            for (item in db.collection(SQUADS_COLLECTION).document(squadId).get().await()
                .get(FIELD_USERS) as List<*>) if (item is String) list.add(item)
            list
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
            listOf()
        }
    }

    suspend fun isSquadFull(squadId: String): Boolean = try {
        (db.collection(SQUADS_COLLECTION).document(squadId).get().await()
            .get(FIELD_USERS) as List<*>).size == MAX_SQUAD_MEMBERS_COUNT
    } catch (e: Exception) {
        Log.d("tag", e.message.toString())
        true
    }


    private suspend fun removeFromSquad(userId: String) {
        try {
            var otherUserId: String? = null
            db.runTransaction { transaction ->
                val docRefProfile = db.collection(PROFILE_DATA_COLLECTION).document(userId)
                val squadId = transaction.get(docRefProfile).getString(FIELD_SQUAD_ID)

                if (squadId != null && squadId != "") {
                    val docRefSquads = db.collection(SQUADS_COLLECTION).document(squadId)

                    val list = mutableListOf<String>()
                    for (item in transaction.get(docRefSquads).get(FIELD_USERS) as List<*>) {
                        if (item is String) list.add(item)
                    }
                    if (list.isNotEmpty()) {
                        list.remove(userId)
                        if (list.size == 1) {
                            deleteHomeTransaction(transaction, squadId)
                            transaction.delete(docRefSquads)
                            transaction.update(
                                db.collection(PROFILE_DATA_COLLECTION).document(list[0]),
                                FIELD_SQUAD_ID,
                                ""
                            )
                            otherUserId = list[0]
                        } else
                            transaction.update(docRefSquads, FIELD_USERS, list)
                    }

                    transaction.update(docRefProfile, FIELD_SQUAD_ID, "")
                }
            }.await()
            handleMyPinsOnSquadRemove(userId)
            handleMyTotemsOnSquadRemove(userId)
            otherUserId?.let {
                handleMyPinsOnSquadRemove(it)
                handleMyTotemsOnSquadRemove(it)
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private suspend fun handleMyPinsOnSquadJoin(
        userId: String,
        squadId: String
    ) {
        try {
            for (pinSnapshot in
            db.collection(CUSTOM_PINS_COLLECTION)
                .whereEqualTo(FIELD_PLACED_BY, userId)
                .get()
                .await().documents.toList()
            ) {
                db.collection(CUSTOM_PINS_COLLECTION).document(pinSnapshot.id).update(
                    FIELD_VISIBLE_TO,
                    squadId,
                )
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private suspend fun handleMyPinsOnSquadRemove(userId: String) {
        try {
            for (pinSnapshot in
            db.collection(CUSTOM_PINS_COLLECTION)
                .whereEqualTo(FIELD_PLACED_BY, userId).get()
                .await().documents.toList()
            ) {
                db.collection(CUSTOM_PINS_COLLECTION).document(pinSnapshot.id).update(
                    FIELD_VISIBLE_TO,
                    ""
                )
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private suspend fun handleMyTotemsOnSquadJoin(
        userId: String,
        squadId: String
    ) {
        try {
            for (totemSnapshot in
            db.collection(TOTEMS_COLLECTION)
                .whereEqualTo(FIELD_PLACED_BY, userId)
                .get()
                .await().documents.toList()
            ) {
                db.collection(CUSTOM_PINS_COLLECTION).document(totemSnapshot.id).update(
                    FIELD_VISIBLE_TO,
                    squadId,
                )
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private suspend fun handleMyTotemsOnSquadRemove(userId: String) {
        try {
            for (totemSnapshot in
            db.collection(TOTEMS_COLLECTION)
                .whereEqualTo(FIELD_PLACED_BY, userId).get()
                .await().documents.toList()
            ) {
                db.collection(CUSTOM_PINS_COLLECTION).document(totemSnapshot.id).update(
                    FIELD_VISIBLE_TO,
                    ""
                )
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun acceptInviteToSquad(inviterId: String, inviteeId: String) {
        try {
            var squadId: String? = null
            var inviterSquadId: String? = null
            getInviteIdOrNull(inviterId, inviteeId)?.let { docId ->
                db.runTransaction {

                    inviterSquadId = it.get(
                        db.collection(PROFILE_DATA_COLLECTION).document(inviterId)
                    ).getString(FIELD_SQUAD_ID)

                    if (inviterSquadId == null || inviterSquadId == "") {
                        squadId = createSquadAndReturnSquadId(it, inviterId, inviteeId)

                    } else {
                        addUserToSquadAndUpdateUser(it, inviterSquadId!!, inviteeId)
                    }

                    it.delete(db.collection(SQUAD_INVITES_COLLECTION).document(docId))

                }.await()
                if (squadId != null) {
                    handleMyPinsOnSquadJoin(inviteeId, squadId!!)
                    handleMyPinsOnSquadJoin(inviterId, squadId!!)
                    handleMyTotemsOnSquadJoin(inviteeId, squadId!!)
                    handleMyTotemsOnSquadJoin(inviterId, squadId!!)
                    addHome(squadId!!, null)
                    onAcceptInviteMergeHouse(inviteeId, squadId!!)
                    onAcceptInviteMergeHouse(inviterId, squadId!!)
                } else {
                    handleMyPinsOnSquadJoin(inviteeId, inviterSquadId!!)
                    handleMyTotemsOnSquadJoin(inviteeId, inviterSquadId!!)
                    onAcceptInviteMergeHouse(inviteeId, inviterSquadId!!)
                }

            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun declineInviteToSquad(inviterId: String, inviteeId: String) {
        try {
            getInviteIdOrNull(inviterId, inviteeId)?.let {
                db.collection(SQUAD_INVITES_COLLECTION).document(it).delete()
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    private suspend fun getInviteIdOrNull(inviterId: String, inviteeId: String): String? = try {
        db.collection(SQUAD_INVITES_COLLECTION).whereEqualTo(FIELD_INVITER_ID, inviterId)
            .whereEqualTo(FIELD_INVITEE_ID, inviteeId).get().await().firstOrNull()?.id
    } catch (e: Exception) {
        Log.d("tag", e.message.toString())
        null
    }

    suspend fun initKickVote(
        myId: String,
        squad_id: String,
        user_id: String,
    ) {
        try {
            val list = getSquadUserIds(squad_id)

            if (list.size <= 2) {
                removeFromSquad(user_id)
            } else {
                if (db.collection(KICK_VOTE_COLLECTION).whereEqualTo(FIELD_SQUAD_ID, squad_id)
                        .whereEqualTo(FIELD_USER_ID, user_id).get().await().firstOrNull() == null
                ) {
                    val votes = mutableMapOf<String, Vote>()
                    list.forEach {
                        votes[it] = when (it) {
                            user_id -> Vote.No
                            myId -> Vote.Yes
                            else -> Vote.Unanswered
                        }
                    }
                    db.collection(KICK_VOTE_COLLECTION).document().set(
                        KickVoteData(
                            squad_id = squad_id, user_id = user_id, votes = votes
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    suspend fun kickVote(myId: String, squadId: String, userId: String, myVote: Vote) {
        try {
            val squadNum = (db.collection(SQUADS_COLLECTION).document(squadId).get().await()
                .get(FIELD_USERS) as List<*>).size

            val id = db.collection(KICK_VOTE_COLLECTION).whereEqualTo(FIELD_SQUAD_ID, squadId)
                .whereEqualTo(FIELD_USER_ID, userId).get().await().firstOrNull()?.id

            if (id != null) {
                db.collection(KICK_VOTE_COLLECTION).document(id).get().await()
                    .toObject(KickVoteData::class.java)?.let {
                        val newMap = it.votes?.toMutableMap() ?: mutableMapOf()
                        newMap[myId] = myVote
                        var voteYes = 0
                        var voteNo = 0
                        for (vote in newMap.values) {
                            when (vote) {
                                Vote.Unanswered -> {}
                                Vote.No -> voteNo++
                                Vote.Yes -> voteYes++
                            }
                        }

                        ((squadNum + 1) / 2).let { votesHalf ->
                            if (votesHalf <= voteYes) {//kick
                                removeFromSquad(userId)
                                db.collection(KICK_VOTE_COLLECTION).document(id).delete().await()
                                reevaluateVoteAfterKick(userId, squadId)
                            } else if (votesHalf <= voteNo) {
                                db.collection(KICK_VOTE_COLLECTION).document(id).delete().await()

                            } else {
                                db.collection(KICK_VOTE_COLLECTION).document(id).set(
                                    it.copy(votes = newMap)
                                ).await()
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }

    }

    private suspend fun reevaluateVoteAfterKick(userId: String, squadId: String) {
        try {
            val kicksList =
                db.collection(KICK_VOTE_COLLECTION).whereEqualTo(FIELD_SQUAD_ID, squadId).get()
                    .await().documents.toList()

            val squadNumNull = db.collection(SQUADS_COLLECTION).document(squadId).get().await()

            val users = squadNumNull.get(FIELD_USERS)
            if (users != null) {
                val squadNum = (users as List<*>).size
                for (itemDoc in kicksList) {
                    val item = itemDoc.toObject(KickVoteData::class.java)
                    if (item != null) {
                        val newMap = item.votes?.toMutableMap() ?: mutableMapOf()
                        newMap.remove(userId)
                        var voteYes = 0
                        var voteNo = 0
                        for (vote in newMap.values) {
                            when (vote) {
                                Vote.Unanswered -> {}
                                Vote.No -> voteNo++
                                Vote.Yes -> voteYes++
                            }
                        }

                        (squadNum / 2).let { votesHalf ->
                            if (votesHalf <= voteYes) {//kick
                                removeFromSquad(userId)
                                db.collection(KICK_VOTE_COLLECTION).document(itemDoc.id).delete()
                                    .await()
                                reevaluateVoteAfterKick(userId, squadId)
                            } else if (votesHalf <= voteNo) {
                                db.collection(KICK_VOTE_COLLECTION).document(itemDoc.id).delete()
                                    .await()

                            } else {
                                db.collection(KICK_VOTE_COLLECTION).document(itemDoc.id).set(
                                    item.copy(votes = newMap)
                                ).await()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }

    }

    suspend fun leaveSquad(myId: String, squadId: String) {
        try {
            removeFromSquad(myId)
            reevaluateVoteAfterKick(myId, squadId)
        } catch (e: Exception) {
            Log.d("tag", e.message.toString())
        }
    }

    //endregion

    //region search
    private val userGeoFirestore = GeoFirestore(db.collection(PROFILE_DATA_COLLECTION))
    private val resourcesGeoFirestore =
        GeoFirestore(db.collection(FireStoreConstants.RESOURCES_COLLECTION))

    private fun searchHandler(
        geoFirestore: GeoFirestore,
        onSearchCompleteCallback: (List<DocumentSnapshot>?) -> Unit,
        onSearchFailedCallback: () -> Unit,
        userLocation: GeoPoint,
        radius: Double,
    ) {
        geoFirestore.getAtLocation(userLocation, radius) { docs, ex ->
            if (ex != null) {
                onSearchFailedCallback()
                return@getAtLocation
            } else {
                onSearchCompleteCallback(docs)
            }
        }
    }

    fun searchUsersInRadius(
        userLocation: GeoPoint,
        radius: Double,
        onSearchCompleteCallback: (List<DocumentSnapshot>?) -> Unit,
        onSearchFailedCallback: () -> Unit
    ) {
        searchHandler(
            userGeoFirestore, onSearchCompleteCallback, onSearchFailedCallback, userLocation, radius
        )
    }


    fun searchResourcesInRadius(
        userLocation: GeoPoint,
        radius: Double,
        onSearchCompleteCallback: (List<DocumentSnapshot>?) -> Unit,
        onSearchFailedCallback: () -> Unit
    ) {
        searchHandler(
            resourcesGeoFirestore,
            onSearchCompleteCallback,
            onSearchFailedCallback,
            userLocation,
            radius
        )
    }
    //endregion
}

