package mosis.streetsandtotems.feature_auth.presentation.signin

import kotlinx.coroutines.flow.StateFlow
import mosis.streetsandtotems.core.presentation.states.FormState
import mosis.streetsandtotems.feature_auth.presentation.util.SignInFields

data class SignInState(
    val formState: FormState<SignInFields>,
    val signInScreenEvents: StateFlow<SignInScreenEvents?>
)