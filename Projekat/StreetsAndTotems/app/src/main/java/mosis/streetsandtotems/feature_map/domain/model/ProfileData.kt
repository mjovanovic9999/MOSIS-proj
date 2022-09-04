package mosis.streetsandtotems.feature_map.domain.model

import android.net.Uri
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.core.domain.model.PrivacySettings

data class ProfileData(
    override val id: String? = null,
    override val l: GeoPoint? = null,
    val user_name: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val phone_number: String? = null,
    val squad_id: String? = null,
    val email: String? = null,
    val image: Uri? = null,
    val call_privacy_level: PrivacySettings? = null,
    val messaging_privacy_level: PrivacySettings? = null,
    @field:JvmField
    val is_online: Boolean? = null
) : Data
