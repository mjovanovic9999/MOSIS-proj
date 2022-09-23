package mosis.streetsandtotems.feature_auth.presentation.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileFields(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val imagePath: String,
    val userName: String
) : Parcelable

