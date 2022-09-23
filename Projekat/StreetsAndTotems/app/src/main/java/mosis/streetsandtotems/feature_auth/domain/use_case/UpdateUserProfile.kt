package mosis.streetsandtotems.feature_auth.domain.use_case

import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import mosis.streetsandtotems.feature_auth.presentation.util.ProfileFields

class UpdateUserProfile(private val repository: AuthRepository) {
    suspend operator fun invoke(newUserData: ProfileFields) =
        repository.updateUserProfile(newUserData)
}