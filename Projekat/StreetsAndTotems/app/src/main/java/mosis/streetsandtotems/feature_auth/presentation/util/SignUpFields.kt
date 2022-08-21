package mosis.streetsandtotems.feature_auth.presentation.util

import mosis.streetsandtotems.feature_auth.domain.model.User

data class SignUpFields(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val repeatPassword: String
) {
    fun getUser(): User =
        User(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            email = email,
            password = password,
            photoUri = null
        )
}