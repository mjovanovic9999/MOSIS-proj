package mosis.streetsandtotems.feature_auth.domain.model

data class SignInError(val wrongEmail: Boolean, val wrongPassword: Boolean)
