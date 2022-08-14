package mosis.streetsandtotems.feature_auth.presentation.signup

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomImageSelectorAndCropper
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtons
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtonsType
import mosis.streetsandtotems.ui.theme.sizes

@AuthNavGraph
@Destination
@Composable
fun SignUpScreen(viewModel: SignupViewModel) {
    CustomPage(
        titleText = TitleConstants.SIGN_UP,
        content = {
            Form(formState = viewModel.formState, asColumn = false)

            CustomImageSelectorAndCropper()

            CustomButton(
                matchParentWidth = true,
                clickHandler = { viewModel.formState.validateAndFocusFirstInvalidField() },
                text = ButtonConstants.SIGN_UP,
                buttonType = CustomButtonType.Outlined,
                buttonModifier = Modifier.padding(top = MaterialTheme.sizes.sign_up_button_top_padding),
                textStyle = MaterialTheme.typography.titleMedium,
                enabled = viewModel.formState.isFormFilled.value
            )
            AuthButtons(type = AuthButtonsType.SignUp)
        },
        scrollable = true
    )
}