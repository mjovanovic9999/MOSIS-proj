package mosis.streetsandtotems.core.domain.use_case

import mosis.streetsandtotems.core.data.data_source.AuthProvider
import mosis.streetsandtotems.core.domain.repository.PreferenceRepository

class UpdateAuthProvider(private val preferenceRepository: PreferenceRepository) {
    suspend fun invoke(authProvider: AuthProvider) =
        preferenceRepository.saveAuthProvider(authProvider = authProvider)
}