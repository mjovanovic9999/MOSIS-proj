package mosis.streetsandtotems.feature_auth.domain.model

data class SignUpFields(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val username: String,
    val password: String,
    val repeatPassword: String
)