package mosis.streetsandtotems.feature_auth.presentation.components

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.auth.api.identity.BeginSignInResult

@Composable
fun OneTapGoogle(signInResult: BeginSignInResult?, onAccountSelected: (intent: Intent?) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                onAccountSelected(result.data)
            }
        }

    LaunchedEffect(signInResult) {
        signInResult?.let {
            val intent =
                IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
            launcher.launch(intent)
        }
    }
}