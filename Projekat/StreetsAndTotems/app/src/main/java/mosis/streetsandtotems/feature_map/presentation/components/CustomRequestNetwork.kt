package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationReceiver

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
        confirmButton = {
//            TextButton(
//                onClick = {
//                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                }
//            ) {
//                Text(ButtonConstants.DIALOG_LOCATION_CONFIRM_BUTTON)
//            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    PendingIntent.getBroadcast(
                        context,
                        1,
                        Intent(context, NotificationReceiver::class.java),
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
                    ).send()

                    Log.d("tag", "ALOOOOOOOOOOOOOOOO")

                    (context as Activity).finishAndRemoveTask()//ugsasi servis
                }
            ) {
                Text(ButtonConstants.DIALOG_NETWORK_DISMISS_BUTTON)
            }
        }
    )

}