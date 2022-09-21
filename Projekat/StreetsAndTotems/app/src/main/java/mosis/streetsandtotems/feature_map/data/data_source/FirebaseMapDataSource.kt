package mosis.streetsandtotems.feature_map.data.data_source

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Transaction
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.FireStoreConstants
import mosis.streetsandtotems.core.FireStoreConstants.EASY_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVENTORY
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVITEE_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_INVITER_ID
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_SQUAD_ID
import mosis.streetsandtotems.core.FireStoreConstants.HARD_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ITEM_COUNT
import mosis.streetsandtotems.core.FireStoreConstants.KICK_VOTE_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.LEADERBOARD_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.MARKET_DOCUMENT_ID
import mosis.streetsandtotems.core.FireStoreConstants.MEDIUM_RIDDLES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.ORDER_NUMBER
import mosis.streetsandtotems.core.FireStoreConstants.PROFILE_DATA_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.RIDDLE_COUNT_VALUE
import mosis.streetsandtotems.core.FireStoreConstants.SQUADS_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.SQUAD_INVITES_COLLECTION
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_USERS
import mosis.streetsandtotems.core.FireStoreConstants.FIELD_USER_ID
import mosis.streetsandtotems.core.FireStoreConstants.L
import mosis.streetsandtotems.core.PointsConversion.MAX_SQUAD_MEMBERS_COUNT
import mosis.streetsandtotems.core.PointsConversion.SQUAD_MEMBERS_POINTS_COEFFICIENT
import mosis.streetsandtotems.feature_map.domain.model.*
import kotlin.random.Random

class FirebaseMapDataSource(private val db: FirebaseFirestore) {
    suspend fun addCustomPin(
        l: GeoPoint,
        visible_to: String,
        placed_by: String,
        text: String,
    ) {
        db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).add(
            mapOf(
                L to l,
                "text" to text,
                "placed_by" to placed_by,
                "visible_to" to visible_to,
            )
        ).await()
    }

    suspend fun updateCustomPin(
        id: String,
        visible_to: String?,
        placed_by: String?,
        text: String?,
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (visible_to != null) data["placed_by"] = placed_by as Any
        if (placed_by != null) data["placed_by"] = placed_by as Any
        if (text != null) data["text"] = text as Any

        if (data.isNotEmpty()) db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).document(id)
            .update(data).await()
    }

    suspend fun deleteCustomPin(id: String) {
        db.collection(FireStoreConstants.CUSTOM_PINS_COLLECTION).document(id).delete().await()
    }

    suspend fun addHome(myId: String, l: GeoPoint) {
        db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).set(
            mapOf(
                L to l,
                FIELD_INVENTORY to mapOf(
                    "wood" to 0,
                    "brick" to 0,
                    "stone" to 0,
                    "emerald" to 0,
                    "totem" to 0,
                ),
            )
        ).await()
    }

    suspend fun updateHome(
        homeId: String, newInventoryData: InventoryData? = null, l: GeoPoint? = null
    ) {
        val data: MutableMap<String, Any> = mutableMapOf()
        if (newInventoryData != null) data[FIELD_INVENTORY] = newInventoryData
        if (l != null) data[L] = l

        if (data.isNotEmpty()) db.collection(FireStoreConstants.HOMES_COLLECTION).document(homeId)
            .update(data).await()
    }

    suspend fun deleteHome(myId: String) {
        db.collection(FireStoreConstants.HOMES_COLLECTION).document(myId).delete().await()

    }

    suspend fun updateResource(resourceId: String, remaining: Int) {
        db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).update(
            mapOf("remaining" to remaining)
        ).await()
    }

    suspend fun deleteResource(resourceId: String) {
        db.collection(FireStoreConstants.RESOURCES_COLLECTION).document(resourceId).delete().await()
    }

    suspend fun getUserInventory(userId: String): UserInventoryData? {
        return db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(userId).get()
            .await().toObject(UserInventoryData::class.java)
    }

    suspend fun updateUserInventory(
        myId: String, newUserInventoryData: UserInventoryData
    ) {
        db.collection(FireStoreConstants.USER_INVENTORY_COLLECTION).document(myId)
            .set(newUserInventoryData).await()
    }

    fun updateUserOnlineStatus(isOnline: Boolean, userId: String): Task<Void> {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId)
            .update(FireStoreConstants.IS_ONLINE_FIELD, isOnline)
    }

    suspend fun getUserData(userId: String): UserData? {
        return db.collection(FireStoreConstants.PROFILE_DATA_COLLECTION).document(userId).get()
            .await().toObject(UserData::class.java)
    }

    suspend fun updateMarket(newMarket: Map<String, MarketItem>) {
        db.collection(FireStoreConstants.MARKET_COLLECTION).document(MARKET_DOCUMENT_ID)
            .update(mapOf("items" to newMarket)).await()
    }

    suspend fun updateTotem(totemId: String, newTotem: TotemData) {
        db.collection(FireStoreConstants.TOTEMS_COLLECTION).document(totemId).set(newTotem).await()
    }

    suspend fun deleteTotem(totemId: String) {
        db.collection(FireStoreConstants.TOTEMS_COLLECTION).document(totemId).delete().await()
    }

    suspend fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel): RiddleData? {
        val collection = when (protectionLevel) {
            ProtectionLevel.RiddleProtectionLevel.Low -> EASY_RIDDLES_COLLECTION
            ProtectionLevel.RiddleProtectionLevel.Medium -> MEDIUM_RIDDLES_COLLECTION
            ProtectionLevel.RiddleProtectionLevel.High -> HARD_RIDDLES_COLLECTION
        }

        val count = db.collection(collection).document(ITEM_COUNT).get().await()
            .getLong(RIDDLE_COUNT_VALUE)
        val riddles =
            db.collection(collection)
                .whereEqualTo(
                    ORDER_NUMBER,
                    Random.nextInt(0, count?.toInt() ?: 10)
                )
                .get()
                .await().toObjects(RiddleData::class.java)

        return riddles[0]
    }

    suspend fun updateLeaderboard(userId: String, addLeaderboardPoints: Int) {
        db.runTransaction {
            val docRef = db.collection(LEADERBOARD_COLLECTION).document(userId)
            val points = it.get(docRef).getLong("points")
            it.update(docRef, "points", (points ?: 0) + addLeaderboardPoints)
        }.await()
    }

    suspend fun updateSquadLeaderboard(squadId: String, addSquadLeaderboardPoints: Int) {
        val ids =
            db.collection(SQUADS_COLLECTION).document("ScrImM23lmrLjRL0oMiz"/*squadId*/)
                .get()///////////////////
                .await().get(FIELD_USERS) as List<*>
        for (item in ids) {
            if (item is String)
                updateLeaderboard(
                    item,
                    (addSquadLeaderboardPoints * SQUAD_MEMBERS_POINTS_COEFFICIENT).toInt()
                )
        }
    }

    //region squad interaction

    private fun addUserToSquadAndUpdateUser(
        transaction: Transaction,
        squadId: String,
        inviteeId: String
    ) {
        val docRef = db.collection(SQUADS_COLLECTION).document(squadId)

        val list = mutableListOf<String>()
        for (item in transaction.get(docRef).get(FIELD_USERS) as List<*>) {
            if (item is String)
                list.add(item)
        }
        if (list.isNotEmpty()) {
            list.add(inviteeId)
            transaction.update(docRef, FIELD_USERS, list)
        }

        transaction.update(
            db.collection(PROFILE_DATA_COLLECTION).document(inviteeId),
            FIELD_SQUAD_ID,
            squadId
        )
    }

    private fun createSquad(
        transaction: Transaction,
        inviterId: String,
        inviteeId: String
    ) {
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
    }

    //check da l je user vec u squad i da l sam ga vec pozvao
    suspend fun initInviteToSquad(inviterId: String, inviteeId: String) {//treb se i pretplati
        db.collection(SQUAD_INVITES_COLLECTION).document().set(
            mapOf(
                FIELD_INVITER_ID to inviterId,
                FIELD_INVITEE_ID to inviteeId,
            )
        ).await()
    }

    suspend fun isUserInSquad(inviteeId: String) =
        db.collection(PROFILE_DATA_COLLECTION).document(inviteeId).get().await()
            .getString(FIELD_SQUAD_ID).let {
                !(it == null || it == "")
            }

    suspend fun isSquadFull(squadId: String): Boolean =
        (db.collection(SQUADS_COLLECTION).document(squadId).get().await()
            .get(FIELD_USERS) as List<*>).size == MAX_SQUAD_MEMBERS_COUNT


    suspend fun acceptInviteToSquad(inviterId: String, inviteeId: String) {
        getInviteIdOrNull(inviterId, inviteeId)?.let { docId ->
            db.runTransaction {

                val inviterSquadId = it.get(
                    db.collection(PROFILE_DATA_COLLECTION).document(inviterId)
                ).getString(FIELD_SQUAD_ID)

                if (inviterSquadId == null || inviterSquadId == "") {
                    createSquad(it, inviterId, inviteeId)
                } else {
                    addUserToSquadAndUpdateUser(it, inviterSquadId, inviteeId)
                }

                it.delete(db.collection(SQUAD_INVITES_COLLECTION).document(docId))

            }.await()
        }
    }

    suspend fun removeFromSquad(userId: String) {
        db.runTransaction { transaction ->
            val docRefProfile = db.collection(PROFILE_DATA_COLLECTION).document(userId)
            val squadId = transaction.get(docRefProfile).getString(FIELD_SQUAD_ID)

            if (squadId != null && squadId != "") {
                val docRefSquads = db.collection(SQUADS_COLLECTION).document(squadId)

                val list = mutableListOf<String>()
                for (item in transaction.get(docRefSquads).get(FIELD_USERS) as List<*>) {
                    if (item is String)
                        list.add(item)
                }
                if (list.isNotEmpty()) {
                    list.remove(userId)
                    if (list.size == 1) {
                        transaction.delete(docRefSquads)
                        transaction.update(
                            db.collection(PROFILE_DATA_COLLECTION).document(list[0]),
                            FIELD_SQUAD_ID,
                            ""
                        )
                    } else
                        transaction.update(docRefSquads, FIELD_USERS, list)
                }

                transaction.update(docRefProfile, FIELD_SQUAD_ID, "")

            }
        }.await()
    }

    suspend fun declineInviteToSquad(inviterId: String, inviteeId: String) =
        getInviteIdOrNull(inviterId, inviteeId)?.let {
            db.collection(SQUAD_INVITES_COLLECTION).document(it).delete()
        }

    private suspend fun getInviteIdOrNull(inviterId: String, inviteeId: String): String? =
        db.collection(SQUAD_INVITES_COLLECTION)
            .whereEqualTo(FIELD_INVITER_ID, inviterId)
            .whereEqualTo(FIELD_INVITEE_ID, inviteeId)
            .get().await().firstOrNull()?.id


    suspend fun initKickVote(kickVote: KickVoteData) {//userid je odma glasao za No i ostali unanswewre
        val list = db.collection(SQUADS_COLLECTION).document(kickVote.squad_id ?: "").get().await()
            .get(FIELD_USERS) as List<*>

        if (list.size <= 2) {
            removeFromSquad(kickVote.user_id ?: "")

        } else {
            if (db.collection(KICK_VOTE_COLLECTION)
                    .whereEqualTo(FIELD_SQUAD_ID, kickVote.squad_id)
                    .whereEqualTo(FIELD_USER_ID, kickVote.user_id)
                    .get().await().firstOrNull() == null
            )
                db.collection(KICK_VOTE_COLLECTION).document().set(kickVote)
        }
    }//treba se pretplate ostali

    suspend fun kickVote(myId: String, squadId: String, userId: String, myVote: Vote) {
        val squadNum = (db.collection(SQUADS_COLLECTION).document(squadId).get().await()
            .get(FIELD_USERS) as List<*>).size

        val id = db.collection(KICK_VOTE_COLLECTION)
            .whereEqualTo(FIELD_SQUAD_ID, squadId)
            .whereEqualTo(FIELD_USER_ID, userId)
            .get().await().firstOrNull()?.id

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

    }

//endregion
}

