package mosis.streetsandtotems.feature_auth.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph

@AuthNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(viewModel: SignInViewModel, destinationsNavigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_text),
            contentDescription = ImageContentDescriptionConstants.LOGO_TEXT
        )
        Form(formState = viewModel.formState, spacing = 0.dp)
        CustomButton(
            clickHandler = { /*TODO*/ },
            text = ButtonConstants.FORGOT_PASSWORD,
            buttonType = CustomButtonType.Text,
            buttonModifier = Modifier
        )
        CustomButton(
            clickHandler = { /*TODO*/ },
            text = ButtonConstants.SIGN_IN,
            buttonType = CustomButtonType.Outlined,
            m
        )
    }
}

