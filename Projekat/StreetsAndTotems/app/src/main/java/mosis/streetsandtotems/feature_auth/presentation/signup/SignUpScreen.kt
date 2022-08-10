package mosis.streetsandtotems.feature_auth.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomImageSelectorAndCropper
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.AuthNavGraph
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtons
import mosis.streetsandtotems.feature_auth.presentation.components.AuthButtonsType
import mosis.streetsandtotems.ui.theme.sizes

@AuthNavGraph
@Destination
@Composable
fun SignUpScreen(viewModel: SignupViewModel) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = TitleConstants.SIGN_UP,
            style = MaterialTheme.typography.displaySmall.plus(TextStyle(fontWeight = FontWeight.ExtraBold)),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawVerticalScrollbar(scrollState)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(MaterialTheme.sizes.auth_screen_form_width)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                viewModel.formState.fields.forEach {
                    it.Content()
                }

                CustomImageSelectorAndCropper()

                CustomButton(
                    matchParentWidth = true,
                    clickHandler = { /*TODO*/ },
                    text = ButtonConstants.SIGN_UP,
                    buttonType = CustomButtonType.Outlined,
                    buttonModifier = Modifier.padding(top = MaterialTheme.sizes.sign_up_button_top_padding),
                    textStyle = MaterialTheme.typography.titleMedium
                )
                AuthButtons(type = AuthButtonsType.SignUp)
            }
        }
    }
}