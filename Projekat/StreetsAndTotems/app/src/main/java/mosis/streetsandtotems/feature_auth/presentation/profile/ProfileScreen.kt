package mosis.streetsandtotems.feature_auth.presentation.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.ui.theme.sizes

@RootNavGraph
@Destination
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {

    BackHandler(enabled = viewModel.editMode.value) {
        viewModel.changeMode()
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(MaterialTheme.sizes.default_form_width)
                .align(
                    Alignment.TopCenter
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.profile_screen_spacing)
            ) {
                Text(
                    text = if (viewModel.editMode.value) TitleConstants.EDIT_PROFILE else TitleConstants.PROFILE,
                    style = MaterialTheme.typography.displaySmall.plus(TextStyle(fontWeight = FontWeight.ExtraBold)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            RoundedCornerShape(MaterialTheme.sizes.image_corner)
                        )
                )
                Form(viewModel.formState.value, spacing = MaterialTheme.sizes.none)
            }
            if (viewModel.editMode.value) {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    buttonType = CustomButtonType.Outlined,
                    text = ButtonConstants.SAVE,
                    matchParentWidth = true,
                    textStyle = MaterialTheme.typography.titleMedium,
                    buttonModifier = Modifier.padding(bottom = MaterialTheme.sizes.profile_screen_spacing),
                    enabled = viewModel.formState.value.isFormFilled.value
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.sizes.profile_screen_spacing)
                ) {
                    CustomButton(
                        clickHandler = { viewModel.changeMode() },
                        buttonType = CustomButtonType.Outlined,
                        text = ButtonConstants.EDIT_PROFILE,
                        matchParentWidth = true,
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                    CustomButton(
                        clickHandler = { viewModel.showEditPasswordDialog() },
                        buttonType = CustomButtonType.Outlined,
                        text = ButtonConstants.CHANGE_PASSWORD,
                        matchParentWidth = true,
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }

    if (viewModel.editPasswordDialogOpen.value)
        AlertDialog(
            onDismissRequest = { viewModel.hideEditPasswordDialog() },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = ImageContentDescriptionConstants.EDIT_PASSWORD,
                    modifier = Modifier.size(MaterialTheme.sizes.title_icon)
                )
            },
            title = {
                Text(
                    text = TitleConstants.EDIT_PASSWORD,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Form(formState = viewModel.editPasswordFormState)
            },
            confirmButton = {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = ButtonConstants.SAVE,
                    buttonType = CustomButtonType.Text,
                    enabled = viewModel.editPasswordFormState.isFormFilled.value,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            },
            dismissButton = {
                CustomButton(
                    clickHandler = { viewModel.hideEditPasswordDialog() },
                    text = ButtonConstants.CANCEL,
                    buttonType = CustomButtonType.Text,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = ButtonDefaults.ContentPadding
                )
            }
        )
}
