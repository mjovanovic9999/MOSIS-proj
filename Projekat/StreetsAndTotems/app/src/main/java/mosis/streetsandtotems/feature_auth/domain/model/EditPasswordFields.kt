package mosis.streetsandtotems.feature_auth.domain.model

data class EditPasswordFields(
    val currentPassword: String,
    val newPassword: String,
    val repeatNewPassword: String
)