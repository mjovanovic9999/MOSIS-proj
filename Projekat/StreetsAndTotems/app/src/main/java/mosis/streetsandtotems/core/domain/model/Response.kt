package mosis.streetsandtotems.core.domain.model

sealed interface Response<out T> {
    object Loading : Response<Nothing>
    data class Success<out T>(val data: T? = null) : Response<T>
    data class Error<out T>(val message: String? = null, val data: T? = null) : Response<T>
}
