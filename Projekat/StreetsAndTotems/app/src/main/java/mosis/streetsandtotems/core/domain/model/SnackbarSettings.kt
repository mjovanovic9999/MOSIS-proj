package mosis.streetsandtotems.core.domain.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.presentation.components.SnackbarType

data class SnackbarSettings(
    val message: String,
    val duration: SnackbarDuration,
    val modifier: Modifier = Modifier,
    val withDismissAction: Boolean = true,
    val actionLabel: String? = null,
    val snackbarType: SnackbarType = SnackbarType.Info,
    val onDismissed: () -> Unit = {},
    val onActionPerformed: () -> Unit = {},
    val actionOnNewLine: Boolean = false,
    val snackbarId: Int
)