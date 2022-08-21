package mosis.streetsandtotems.feature_auth.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: FirebaseAuthDataSource) :
    AuthRepository {
    override fun isUserAuthenticated(): Boolean = authDataSource.getCurrentUser() != null

    override suspend fun emailAndPasswordSignIn(
        email: String,
        password: String
    ): Flow<Response<Nothing>> =
        flow {
            try {
                emit(Response.Loading)
                authDataSource.emailAndPasswordSignIn(email, password).await()
                emit(Response.Success())
            } catch (e: Exception) {
                emit(
                    Response.Error(
                        message = e.message ?: e.toString(),
                    )
                )
            }
        }

    override suspend fun emailAndPasswordSignUp(
        email: String,
        password: String
    ): Flow<Response<Nothing>> =
        flow {
            try {
                emit(Response.Loading)
                authDataSource.emailAndPasswordSignUp(email, password).await()
                emit(Response.Success())
            } catch (e: Exception) {
                emit(
                    Response.Error(
                        message = e.message ?: e.toString(),
                    )
                )
            }
        }

    override suspend fun signOut(): Flow<Response<Nothing>> = flow {
        try {
            authDataSource.signOut()
        } catch (e: Exception) {
            emit(
                Response.Error(
                    message = e.message ?: e.toString()
                )
            )
        }
    }
}