package mosis.streetsandtotems.feature_map.domain.model

import com.google.firebase.firestore.GeoPoint

data class UserInGameData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val calls_allowed: Boolean? = null,
    val messaging_allowed: Boolean? = null,
    val user_profile_data: UserProfileData? = null,
) : IData