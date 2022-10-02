package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFields

class EmailAndPasswordSignUp(private val repository: AuthRepository) {
    suspend operator fun invoke(profileData: SignUpFields, password: String) =
        repository.emailAndPasswordSignUp(password, profileData)
}