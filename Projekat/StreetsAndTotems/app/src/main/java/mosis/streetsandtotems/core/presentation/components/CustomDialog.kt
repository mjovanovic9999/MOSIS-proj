package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview

@Preview
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
    dismissButtonEnabled: Boolean = true,
    buttonType: CustomButtonType = CustomButtonType.Text,
    onConfirmButtonClick: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
    confirmButtonMatchParentWidth: Boolean = false,
    dismissButtonMatchParentWidth: Boolean = false,
    confirmButtonVisible: Boolean = true,
    dismissButtonVisible: Boolean = true,
    clickable: Boolean = false
) {
    lateinit var dialogFocusManager: FocusManager

    if (isOpen)
        AlertDialog(
            modifier = if (clickable) modifier.clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { dialogFocusManager.clearFocus() } else modifier,
            onDismissRequest = onDismissRequest,
            title = title,
            text = {
                text?.invoke()
                dialogFocusManager = LocalFocusManager.current
            },
            confirmButton = {
                if (confirmButtonVisible)
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
                if (dismissButtonVisible)
                    CustomButton(
                        clickHandler = onDismissButtonClick,
                        text = dismissButtonText ?: "",
                        buttonType = buttonType,
                        textStyle = MaterialTheme.typography.titleMedium,
                        matchParentWidth = dismissButtonMatchParentWidth,
                        contentPadding = ButtonDefaults.ContentPadding,
                        enabled = dismissButtonEnabled
                    )
            }
        )
}