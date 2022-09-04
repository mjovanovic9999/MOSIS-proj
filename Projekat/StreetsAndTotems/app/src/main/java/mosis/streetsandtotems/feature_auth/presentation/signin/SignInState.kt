package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.runtime.State
import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.coroutines.flow.SharedFlow
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.presentation.util.SignInFields

data class SignInState(
    val formState: FormState<SignInFields>,
    val signInScreenEvents: SharedFlow<SignInScreenEvents?>,
    val onTapSignInResponse: State<BeginSignInResult?>
)