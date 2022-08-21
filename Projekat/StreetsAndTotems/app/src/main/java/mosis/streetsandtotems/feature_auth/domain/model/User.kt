package mosis.streetsandtotems.feature_auth.domain.model

import android.net.Uri

data class User(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val photoUri: Uri?
)
