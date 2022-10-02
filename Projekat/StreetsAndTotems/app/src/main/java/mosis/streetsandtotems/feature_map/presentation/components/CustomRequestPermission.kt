package mosis.streetsandtotems.feature_map.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_BACKGROUND_PERMISSION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_BACKGROUND_PERMISSION_DISMISS_BUTTON
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_PERMISSION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.ButtonConstants.DIALOG_PERMISSION_DISMISS_BUTTON
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.services.LocationService

@Composable
fun CustomRequestPermission(
    permissionsArray: Array<String>,
    arePermissionsGranted: MutableState<Boolean>
) {

    val requestBackgroundAndroidVersion =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R //novi and
    val permissionGrantedState = remember { mutableStateOf(true) }
    val permissionBackgroundGrantedState = remember { mutableStateOf(true) }


    val permissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                if (!requestBackgroundAndroidVersion) {
                    arePermissionsGranted.value = true
                }
                permissionGrantedState.value = true

            }
            else -> {
                permissionGrantedState.value = false
                arePermissionsGranted.value = false

            }
        }
    }

    val permissionBackgroundRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (requestBackgroundAndroidVersion) {
                permissionBackgroundGrantedState.value = true
                arePermissionsGranted.value = true

            }

        } else {
            if (requestBackgroundAndroidVersion) {
                permissionBackgroundGrantedState.value = false
                arePermissionsGranted.value = false
            }
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

        if (requestBackgroundAndroidVersion) {
            permissionBackgroundRequest.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    if (!permissionGrantedState.value) {
        CustomRequestPermissionsDialog(
            LocalContext.current,
            launchPermissionActivityResult
        )
    } else if (requestBackgroundAndroidVersion && !permissionBackgroundGrantedState.value) {
        CustomRequestBackgroundPermissionsDialog(
            LocalContext.current,
            launchPermissionBackgroundActivityResult
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
                Text(DIALOG_PERMISSION_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    context.stopService(Intent(context, LocationService::class.java))
                    (context as Activity).finishAndRemoveTask()

                }
            ) {
                Text(DIALOG_PERMISSION_DISMISS_BUTTON)
            }
        }
    )
}

@Composable
fun CustomRequestBackgroundPermissionsDialog(
    context: Context,
    launchPermissionBackgroundActivityResult: ManagedActivityResultLauncher<Intent, ActivityResult>
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
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri =
                        Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    launchPermissionBackgroundActivityResult.launch(intent)

                }
            ) {
                Text(DIALOG_BACKGROUND_PERMISSION_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    context.stopService(Intent(context, LocationService::class.java))
                    (context as Activity).finishAndRemoveTask()
                }
            ) {
                Text(DIALOG_BACKGROUND_PERMISSION_DISMISS_BUTTON)
            }
        }
    )
}