package mosis.streetsandtotems.feature_auth.presentation.util

data class SignUpFields(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val imagePath: String
)