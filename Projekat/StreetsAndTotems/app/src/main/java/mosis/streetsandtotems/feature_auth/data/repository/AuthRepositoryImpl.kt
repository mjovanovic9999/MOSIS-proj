package mosis.streetsandtotems.feature_auth.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mosis.streetsandtotems.core.domain.util.Response
import mosis.streetsandtotems.core.domain.util.ResponseText
import mosis.streetsandtotems.feature_auth.data.data_source.FirebaseAuthDataSource
import mosis.streetsandtotems.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: FirebaseAuthDataSource) :
    AuthRepository {
    override fun isUserAuthenticated(): Boolean = authDataSource.getCurrentUser() != null

    override suspend fun emailAndPasswordSignIn(email: String, password: String): Flow<Response> =
        flow {
            try {
                emit(Response.ResponseLoading)
                authDataSource.emailAndPasswordSignIn(email, password).await()
                emit(Response.ResponseCompleted.Success(null))
            } catch (e: Exception) {
                emit(
                    Response.ResponseCompleted.Error(
                        ResponseText.DynamicString(
                            e.message ?: e.toString()
                        ), data = null
                    )
                )
            }
        }

    override suspend fun emailAndPasswordSignUp(email: String, password: String): Flow<Response> = flow {
        try {
            emit(Response.ResponseLoading)
            authDataSource.emailAndPasswordSignUp(email, password).await()
            emit(Response.ResponseCompleted.Success(null))
        } catch (e: Exception) {
            emit(
                Response.ResponseCompleted.Error(
                    ResponseText.DynamicString(
                        e.message ?: e.toString()
                    ), null
                )
            )
        }
    }

    override suspend fun signOut(): Flow<Response> = flow {
        try {
            emit(Response.ResponseLoading)
            authDataSource.signOut()?.await()
            emit(Response.ResponseCompleted.Success(null))
        } catch (e: Exception) {
            emit(
                Response.ResponseCompleted.Error(
                    ResponseText.DynamicString(
                        e.message ?: e.toString()
                    ), null
                )
            )
        }
    }
}