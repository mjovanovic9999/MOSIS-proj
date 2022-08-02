package mosis.streetsandtotems.core.domain.util

sealed class Response() {
    object ResponseLoading : Response()
    sealed class ResponseCompleted<out T>(val data: T? = null, val message: ResponseText? = null) :
        Response() {
        class Success<out T>(data: T?) : ResponseCompleted<T>(data)
        class Error<out T>(message: ResponseText, data: T? = null) :
            ResponseCompleted<T>(data, message)
    }
}