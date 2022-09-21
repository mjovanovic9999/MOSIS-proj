package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class OneTapSignUpWithGoogle(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.oneTapSignUpWithGoogle()
}