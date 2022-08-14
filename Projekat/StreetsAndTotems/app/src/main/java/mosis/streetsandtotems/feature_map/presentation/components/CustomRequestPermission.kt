package mosis.streetsandtotems.feature_map.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.MessageConstants

@Composable
fun CustomRequestPermission(
    permissionsArray: Array<String> = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) {
    val permissionGrantedState = remember { mutableStateOf(true) }

    val permissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d("tag", "fine")
                permissionGrantedState.value = true

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d("tag", "coarse")
                permissionGrantedState.value = true

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
                Log.d("tag", "bck")
                if (Manifest.permission.ACCESS_BACKGROUND_LOCATION in permissionsArray)
                    permissionGrantedState.value = true

            }
            else -> {
                Log.d("tag", "else")
                permissionGrantedState.value = false
            }
        }
    }

    val launchPermissionActivityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            permissionRequest.launch(permissionsArray)
        }

    SideEffect {
        permissionRequest.launch(permissionsArray)
    }

    if (!permissionGrantedState.value) {
        CustomRequestPermissionsDialog(
            LocalContext.current,
            launchPermissionActivityResult
        )
    }
}

@Composable
fun CustomRequestPermissionsDialog(
    context: Context,
    launchPermissionActivityResult: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                MessageConstants.DIALOG_PERMISSION_TITLE,
            )
        },
        text = {
            Text(
                MessageConstants.DIALOG_PERMISSION_TEXT,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri =
                        Uri.fromParts("package", context.packageName, null)
                    intent.data = uri

                    launchPermissionActivityResult.launch(intent)
                }
            ) {
                Text(MessageConstants.DIALOG_PERMISSION_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    (context as Activity).finishAndRemoveTask()
                }
            ) {
                Text(MessageConstants.DIALOG_PERMISSION_DISMISS_BUTTON)
            }
        }
    )
}