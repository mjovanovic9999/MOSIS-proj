package mosis.streetsandtotems.feature_auth.presentation.util

import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.util.handleResponse
import mosis.streetsandtotems.feature_auth.domain.use_case.AuthUseCases

suspend fun handleSignInWithEmailAndPassword(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    authUseCases: AuthUseCases,
    snackbarFlow: MutableStateFlow<SnackbarSettings?>,
    showLoaderFlow: MutableStateFlow<Boolean>
) {
    return handleResponse(
        authUseCases.emailAndPasswordSignIn(email, password),
        showLoaderFlow,
        snackbarFlow,
        successMessage = MessageConstants.SIGNED_IN,
        errorMessage = MessageConstants.INVALID_CREDENTIALS,
        onSuccess = {
            onSuccess()
        })
}