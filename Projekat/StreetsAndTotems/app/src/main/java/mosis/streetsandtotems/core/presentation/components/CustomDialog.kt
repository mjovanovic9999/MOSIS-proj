package mosis.streetsandtotems.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean = false,
    onDismissRequest: () -> Unit = {},
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButtonText: String? = null,
    dismissButtonText: String? = null,
    confirmButtonEnabled: Boolean = true,
    buttonType: CustomButtonType = CustomButtonType.Text,
    onConfirmButtonClick: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
    confirmButtonMatchParentWidth: Boolean = false,
    dismissButtomMatchParentWidth: Boolean = false
) {
    if (isOpen)
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            title = title,
            text = text,
            confirmButton = {
                CustomButton(
                    clickHandler = onConfirmButtonClick,
                    text = confirmButtonText ?: "",
                    buttonType = buttonType,
                    textStyle = MaterialTheme.typography.titleMedium,
                    enabled = confirmButtonEnabled,
                    matchParentWidth = confirmButtonMatchParentWidth,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            },
            dismissButton = {
                CustomButton(
                    clickHandler = onDismissButtonClick,
                    text = dismissButtonText ?: "",
                    buttonType = buttonType,
                    textStyle = MaterialTheme.typography.titleMedium,
                    matchParentWidth = dismissButtomMatchParentWidth,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            })
}