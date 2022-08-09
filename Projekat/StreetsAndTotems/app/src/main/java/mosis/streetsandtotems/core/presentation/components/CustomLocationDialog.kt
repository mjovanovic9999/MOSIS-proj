package mosis.streetsandtotems.core.presentation.components

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import mosis.streetsandtotems.core.MessageConstants.DIALOG_LOCATION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.MessageConstants.DIALOG_LOCATION_DISMISS_BUTTON
import mosis.streetsandtotems.core.MessageConstants.DIALOG_LOCATION_TEXT
import mosis.streetsandtotems.core.MessageConstants.DIALOG_LOCATION_TITLE

@Composable
fun CustomLocationDialog(activity: Activity, locationDialogOpenState: MutableState<Boolean>) {
    if (locationDialogOpenState.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    DIALOG_LOCATION_TITLE,
                )
            },
            text = {
                Text(
                    DIALOG_LOCATION_TEXT,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        locationDialogOpenState.value = false
                        activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    Text(DIALOG_LOCATION_CONFIRM_BUTTON)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        activity.finishAndRemoveTask()
                    }
                ) {
                    Text(DIALOG_LOCATION_DISMISS_BUTTON)
                }
            }
        )
    }
}