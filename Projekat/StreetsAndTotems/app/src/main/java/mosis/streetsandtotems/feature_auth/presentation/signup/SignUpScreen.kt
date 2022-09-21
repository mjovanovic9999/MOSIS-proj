package mosis.streetsandtotems.feature_auth.presentation.signup

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph
import mosis.streetsandtotems.core.presentation.screens.tiki.TikiScreenContentType
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.destinations.TikiScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtons
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtonsType
import mosis.streetsandtotems.feature_auth.presentation.components.OneTapGoogle
import mosis.streetsandtotems.ui.theme.sizes

@AuthNavGraph
@Destination
@Composable
fun SignUpScreen(viewModel: SignupViewModel, destinationsNavigator: DestinationsNavigator) {
    val state = viewModel.signUpScreenState.value

    LaunchedEffect(Unit) {
        state.signUpScreenEventFlow.collectLatest {
            if (it != null) when (it) {
                SignUpScreenEvents.SignUpSuccessful -> {
                    destinationsNavigator.popBackStack()
                    destinationsNavigator.navigate(TikiScreenDestination(TikiScreenContentType.EmailVerification))
                }
                SignUpScreenEvents.SignUpWithGoogleSuccessful -> {
                    destinationsNavigator.popBackStack()
                    destinationsNavigator.navigate(MainScreenDestination)
                }
            }
        }
    }

    CustomPage(
        titleText = TitleConstants.SIGN_UP, content = {
            Form(formState = state.formState, asColumn = false)

            CustomButton(
                matchParentWidth = true,
                clickHandler = { viewModel.onEvent(SignUpViewModelEvents.SignUpWithEmailAndPassword) },
                text = ButtonConstants.SIGN_UP,
                buttonType = CustomButtonType.Outlined,
                buttonModifier = Modifier.padding(top = MaterialTheme.sizes.sign_up_button_top_padding),
                textStyle = MaterialTheme.typography.titleMedium,
                enabled = state.formState.isFormFilled.value
            )
            AuthButtons(type = AuthButtonsType.SignUp, onSignInWithGoogleClick = {
                viewModel.onEvent(SignUpViewModelEvents.OneTapGoogleSignUp)
            })
        }, scrollable = true
    )

    OneTapGoogle(signInResult = state.oneTapSignUpResult,
        onAccountSelected = { viewModel.onEvent(SignUpViewModelEvents.SignUpWithGoogle(it)) })
}