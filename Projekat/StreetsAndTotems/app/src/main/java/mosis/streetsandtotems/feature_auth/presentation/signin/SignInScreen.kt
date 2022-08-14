package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph
import mosis.streetsandtotems.destinations.MainScreenDestination
import mosis.streetsandtotems.destinations.SignUpScreenDestination
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtons
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtonsType
import mosis.streetsandtotems.ui.theme.sizes

@AuthNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(viewModel: SignInViewModel, destinationsNavigator: DestinationsNavigator) {
    CustomPage(
        titleContent = {
        Image(
            painter = painterResource(id = R.drawable.logo_text),
            contentDescription = ImageContentDescriptionConstants.LOGO_TEXT,
            modifier = Modifier.padding(top = MaterialTheme.sizes.text_logo_top_padding)
        )
    },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.forgot_password_top_padding)) {
                Form(formState = viewModel.formState, spacing = MaterialTheme.sizes.none)
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = ButtonConstants.FORGOT_PASSWORD,
                    buttonType = CustomButtonType.Text,
                    buttonModifier = Modifier.align(Alignment.Start),
                    textStyle = MaterialTheme.typography.labelMedium,
                )
            }
            CustomButton(
                matchParentWidth = true,
                clickHandler = { destinationsNavigator.navigate(MainScreenDestination) },
                text = ButtonConstants.SIGN_IN,
                buttonType = CustomButtonType.Outlined,
                buttonModifier = Modifier,
                textStyle = MaterialTheme.typography.titleMedium,
                enabled = viewModel.formState.isFormFilled.value
            )

            AuthButtons(type = AuthButtonsType.SignIn)

            CustomButton(
                clickHandler = { destinationsNavigator.navigate(SignUpScreenDestination) },
                text = ButtonConstants.NO_ACCOUNT,
                buttonType = CustomButtonType.Text,
                buttonModifier = Modifier.align(Alignment.CenterHorizontally),
                textStyle = MaterialTheme.typography.labelLarge
            )
        })
}

