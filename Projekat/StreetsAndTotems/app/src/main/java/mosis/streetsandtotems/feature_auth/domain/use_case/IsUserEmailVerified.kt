package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class IsUserEmailVerified(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.isCurrentUserEmailValidated()
}