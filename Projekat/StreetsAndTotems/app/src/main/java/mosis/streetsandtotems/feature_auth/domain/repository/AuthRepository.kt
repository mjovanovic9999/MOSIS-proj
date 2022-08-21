package mosis.streetsandtotems.feature_auth.domain.repository

import kotlinx.coroutines.flow.Flow
import mosis.streetsandtotems.core.domain.model.Response

interface AuthRepository {
    fun isUserAuthenticated(): Boolean

    suspend fun emailAndPasswordSignIn(email: String, password: String): Flow<Response<Nothing>>

    suspend fun emailAndPasswordSignUp(email: String, password: String): Flow<Response<Nothing>>

    suspend fun signOut(): Flow<Response<Nothing>>
}