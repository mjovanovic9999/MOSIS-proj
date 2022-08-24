package mosis.streetsandtotems.feature_auth.presentation.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.components.form.Form
import mosis.streetsandtotems.feature_auth.presentation.components.EditPasswordDialog
import mosis.streetsandtotems.ui.theme.sizes

@RootNavGraph
@Destination
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    BackHandler(enabled = viewModel.editMode.value) {
        viewModel.changeMode()
    }

    CustomPage(
        titleText = if (viewModel.editMode.value) TitleConstants.EDIT_PROFILE else TitleConstants.PROFILE,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.profile_screen_spacing)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
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
        }, contentVerticalArrangement = Arrangement.SpaceBetween
    )
    EditPasswordDialog(
        isOpen = viewModel.editPasswordDialogOpen.value,
        onDismissRequest = { viewModel.hideEditPasswordDialog() },
        editPasswordFormState = viewModel.editPasswordFormState
    )
}