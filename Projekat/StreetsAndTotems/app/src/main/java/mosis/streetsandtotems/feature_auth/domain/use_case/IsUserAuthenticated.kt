package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class IsUserAuthenticated(private val repository: AuthRepository) {
    operator fun invoke() = repository.isUserAuthenticated()
}