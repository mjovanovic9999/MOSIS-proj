package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class UpdatePassword(private val repository: AuthRepository) {
    suspend operator fun invoke(password: String) = repository.updateUserPassword(password)
}