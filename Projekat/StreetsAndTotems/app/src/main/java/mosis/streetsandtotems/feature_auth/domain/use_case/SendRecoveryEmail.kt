package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class SendRecoveryEmail(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.sendRecoveryEmail(email)
}