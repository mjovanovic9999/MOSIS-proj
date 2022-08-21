package mosis.streetsandtotems.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.ButtonConstants

enum class ConfirmationDialogType {
    Info,
    Confirm
}

@Composable
fun CustomConfirmationDialog(
    type: ConfirmationDialogType,
    isOpen: Boolean,
    title: String,
    text: String,
    confirmButtonText: String = ButtonConstants.YES,
    dismissButtonText: String = ButtonConstants.NO,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    onDismissRequest: () -> Unit = {}
) {
    CustomDialog(
        isOpen = isOpen,
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButtonText = confirmButtonText,
        dismissButtonText = dismissButtonText,
        onDismissRequest = onDismissRequest,
        onConfirmButtonClick = onConfirmButtonClick,
        onDismissButtonClick = onDismissButtonClick,
        dismissButtonVisible = type == ConfirmationDialogType.Confirm,
    )
}