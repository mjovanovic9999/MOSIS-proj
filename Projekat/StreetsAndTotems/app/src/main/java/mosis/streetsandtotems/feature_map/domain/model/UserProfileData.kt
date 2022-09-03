package mosis.streetsandtotems.feature_map.domain.model

import android.net.Uri

data class UserProfileData(
    val user_name: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val phone_number: String? = null,
    val squad_id: String? = null,
    val email: String? = null,
    val image: Uri? = null,
)
