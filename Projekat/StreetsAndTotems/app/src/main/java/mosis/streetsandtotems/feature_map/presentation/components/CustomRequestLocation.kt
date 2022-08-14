package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_LOCATION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_LOCATION_DISMISS_BUTTON
import mosis.streetsandtotems.core.MessageConstants

@Composable
fun CustomRequestLocation(
    isLocationEnabled: MutableState<Boolean>
) {
    if (!isLocationEnabled.value) {
        CustomRequestLocationDialog()
    }
}

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
                    (context as Activity).finishAndRemoveTask()
                }
            ) {
                Text(DIALOG_LOCATION_DISMISS_BUTTON)
            }
        }
    )

}