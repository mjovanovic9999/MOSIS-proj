package mosis.streetsandtotems.core.domain.util

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.HandleResponseConstants
import mosis.streetsandtotems.core.domain.model.Response
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.presentation.components.SnackbarType

suspend fun <T : Any> handleResponse(
    responseFlow: Flow<Response<T>>,
    showLoaderFlow: MutableStateFlow<Boolean>? = null,
    snackbarFlow: MutableStateFlow<SnackbarSettings?>? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: ((T?) -> Unit)? = null,
    onError: ((String?, T?) -> Unit)? = null,
    errorMessage: String = "",
    successMessage: String = ""
) {
    responseFlow.collect {
        when (it) {
            is Response.Loading -> {
                showLoaderFlow?.emit(true)
                onLoading?.invoke()
            }
            is Response.Error -> {
                showLoaderFlow?.emit(false)
                snackbarFlow?.emit(
                    SnackbarSettings(
                        message = errorMessage,
                        duration = SnackbarDuration.Short,
                        snackbarType = SnackbarType.Error,
                        snackbarId = snackbarFlow.value?.snackbarId?.plus(other = HandleResponseConstants.ID_ADDITION_FACTOR)
                            ?: HandleResponseConstants.DEFAULT_ID
                    )
                )
                onError?.invoke(it.message, it.data)
            }
            is Response.Success -> {
                showLoaderFlow?.emit(false)
                snackbarFlow?.emit(
                    SnackbarSettings(
                        message = successMessage,
                        duration = SnackbarDuration.Short,
                        snackbarType = SnackbarType.Info,
                        snackbarId = snackbarFlow.value?.snackbarId?.plus(other = HandleResponseConstants.ID_ADDITION_FACTOR)
                            ?: HandleResponseConstants.DEFAULT_ID
                    )
                )
                onSuccess?.invoke(it.data)
            }
        }
    }
}