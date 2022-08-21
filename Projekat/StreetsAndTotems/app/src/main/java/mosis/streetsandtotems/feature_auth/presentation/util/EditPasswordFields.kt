package mosis.streetsandtotems.feature_auth.presentation.util

data class EditPasswordFields(
    val currentPassword: String,
    val newPassword: String,
    val repeatNewPassword: String
)