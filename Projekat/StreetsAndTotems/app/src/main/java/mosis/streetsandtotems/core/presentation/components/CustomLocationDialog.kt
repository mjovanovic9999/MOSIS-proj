package mosis.streetsandtotems.core.presentation.components

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun CustomLocationDialog(activity: Activity, locationDialogOpenState: MutableState<Boolean>) {
    if (locationDialogOpenState.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    "Location disabled",
                )
            },
            text = {
                Text(
                    "In order to use Streets And Totems Location has to be turned on!",
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        locationDialogOpenState.value = false
                        activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    Text("Turn on Location")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        activity.finishAndRemoveTask()
                    }
                ) {
                    Text("Close app")
                }
            }
        )
    }
}