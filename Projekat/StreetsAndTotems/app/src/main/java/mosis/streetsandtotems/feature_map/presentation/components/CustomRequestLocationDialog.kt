package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_LOCATION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_LOCATION_DISMISS_BUTTON
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.services.LocationService

@Composable
fun CustomRequestLocationDialog(
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                MessageConstants.DIALOG_LOCATION_TITLE,
            )
        },
        text = {
            Text(
                MessageConstants.DIALOG_LOCATION_TEXT,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            ) {
                Text(DIALOG_LOCATION_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    context.stopService(Intent(context, LocationService::class.java))
                    (context as Activity).finish()
                }
            ) {
                Text(DIALOG_LOCATION_DISMISS_BUTTON)
            }
        }
    )

}