package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository

class EmailAndPasswordSignIn(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = authRepository.emailAndPasswordSignIn(email, password)
}