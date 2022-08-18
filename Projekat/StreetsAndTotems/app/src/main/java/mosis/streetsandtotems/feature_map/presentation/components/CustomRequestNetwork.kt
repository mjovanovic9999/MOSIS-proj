package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.services.LocationService

@Composable
fun CustomRequestNetwork(networkConnectivityValid: MutableState<Boolean>) {

    if (!networkConnectivityValid.value) {
        CustomRequestNetworkDialog()
    }
}

@Composable
fun CustomRequestNetworkDialog(
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                MessageConstants.DIALOG_NETWORK_TITLE,
            )
        },
        text = {
            Text(
                MessageConstants.DIALOG_NETWORK_TEXT,
            )
        },
        confirmButton = { },
        dismissButton = {
            TextButton(
                onClick = {
                    context.stopService(Intent(context, LocationService::class.java))
                    (context as Activity).finishAndRemoveTask()

                }
            ) {
                Text(ButtonConstants.DIALOG_NETWORK_DISMISS_BUTTON)
            }
        }
    )

}