package mosis.streetsandtotems.feature_map.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.MessageConstants

@Composable
fun CustomRequestPermission(
    permissionsArray: Array<String>,
    requestBackgroundLocationPermission: MutableState<Boolean>,
) {
    val permissionGrantedState = remember { mutableStateOf(true) }
    val permissionBackgroundGrantedState = remember { mutableStateOf(false) }

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
            else -> {
                Log.d("tag", "else")
                permissionGrantedState.value = false
            }
        }
    }

    val permissionBackgroundRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("tag", "back Permission provided by user")
            permissionBackgroundGrantedState.value = true
        } else {
            Log.d("tag", "back  Permission denied by user")
            permissionBackgroundGrantedState.value = false
        }
    }

    val launchPermissionActivityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            permissionRequest.launch(permissionsArray)
        }

    val launchPermissionBackgroundActivityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            permissionBackgroundRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }


    SideEffect {
        permissionRequest.launch(permissionsArray)
        permissionBackgroundRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }

    if (!permissionGrantedState.value) {
        CustomRequestPermissionsDialog(
            LocalContext.current,
            launchPermissionActivityResult
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && requestBackgroundLocationPermission.value && !permissionBackgroundGrantedState.value) {
        Log.d("tag", "abe valjda je teka za stariji androriirdi")

        CustomRequestBackgroundPermissionsDialog(
            LocalContext.current,
            permissionBackgroundRequest,
            requestBackgroundLocationPermission
        )
    }

}


@Composable
fun CustomRequestPermissionsDialog(
    context: Context,
    launchPermissionActivityResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
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

@Composable
fun CustomRequestBackgroundPermissionsDialog(
    context: Context,
    permissionBackgroundRequest: ManagedActivityResultLauncher<String, Boolean>,
    requestBackgroundLocationPermission: MutableState<Boolean>
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                MessageConstants.DIALOG_BACKGROUND_PERMISSION_TITLE,
            )
        },
        text = {
            Text(
                MessageConstants.DIALOG_BACKGROUND_PERMISSION_TEXT,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    permissionBackgroundRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                }
            ) {
                Text(MessageConstants.DIALOG_BACKGROUND_PERMISSION_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    requestBackgroundLocationPermission.value = false
                }
            ) {
                Text(MessageConstants.DIALOG_BACKGROUND_PERMISSION_DISMISS_BUTTON)
            }
        }
    )
}