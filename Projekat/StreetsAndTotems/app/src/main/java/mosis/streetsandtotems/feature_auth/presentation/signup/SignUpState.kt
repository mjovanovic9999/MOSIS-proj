package mosis.streetsandtotems.feature_auth.presentation.signup

import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.coroutines.flow.StateFlow
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.presentation.util.SignUpFields

data class SignUpState(
    val signUpScreenEventFlow: StateFlow<SignUpScreenEvents?>,
    val formState: FormState<SignUpFields>,
    val oneTapSignUpResult: BeginSignInResult?
)
