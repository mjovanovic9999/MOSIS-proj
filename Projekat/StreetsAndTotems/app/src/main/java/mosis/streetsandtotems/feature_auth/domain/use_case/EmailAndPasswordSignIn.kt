package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class EmailAndPasswordSignIn(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repository.emailAndPasswordSignIn(email, password)
}