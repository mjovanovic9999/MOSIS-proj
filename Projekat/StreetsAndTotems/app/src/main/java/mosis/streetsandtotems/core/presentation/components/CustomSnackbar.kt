package mosis.streetsandtotems.core.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import mosis.streetsandtotems.core.domain.model.SnackbarSettings

enum class SnackbarType {
    Info,
    Error
}

@Composable
fun CustomSnackbar(
    snackbarSettingsFlow: StateFlow<SnackbarSettings?>,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarSettingsFlow.collect {
            if (it != null) {
                val snackbarResult = snackbarHostState.showSnackbar(
                    message = it.message,
                    duration = it.duration,
                    withDismissAction = it.withDismissAction,
                    actionLabel = it.actionLabel
                )

                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        snackbarSettingsFlow.value?.onDismissed?.invoke()
                    }
                    SnackbarResult.ActionPerformed -> snackbarSettingsFlow.value?.onActionPerformed?.invoke()
                }
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = snackbarSettingsFlow.value?.modifier ?: Modifier
    ) {
        Snackbar(
            snackbarData = it,
            actionOnNewLine = snackbarSettingsFlow.value?.actionOnNewLine ?: false,
            containerColor = when (snackbarSettingsFlow.value?.snackbarType) {
                SnackbarType.Info -> MaterialTheme.colorScheme.primary
                SnackbarType.Error -> MaterialTheme.colorScheme.errorContainer
                null -> MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = when (snackbarSettingsFlow.value?.snackbarType) {
                SnackbarType.Info -> MaterialTheme.colorScheme.onPrimary
                SnackbarType.Error -> MaterialTheme.colorScheme.onErrorContainer
                null -> MaterialTheme.colorScheme.onSurfaceVariant
            },
            dismissActionContentColor = when (snackbarSettingsFlow.value?.snackbarType) {
                SnackbarType.Info -> MaterialTheme.colorScheme.onPrimary
                SnackbarType.Error -> MaterialTheme.colorScheme.onErrorContainer
                null -> MaterialTheme.colorScheme.onSurfaceVariant
            },
            actionColor = when (snackbarSettingsFlow.value?.snackbarType) {
                SnackbarType.Info -> MaterialTheme.colorScheme.onPrimary
                SnackbarType.Error -> MaterialTheme.colorScheme.onErrorContainer
                null -> MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
    }
}