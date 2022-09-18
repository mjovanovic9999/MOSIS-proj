package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.feature_map.domain.model.RiddleData
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomClaimTotemDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onClaim: () -> Unit,
    backpackHasEmptySpace: Boolean,
) {
    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = {
            onDismissRequest()
        },
        text = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                    contentDescription = ImageContentDescriptionConstants.TOTEM,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)

                )
            }
        },
        buttonType = CustomButtonType.Outlined,
        dismissButtonVisible = false,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = true,
        confirmButtonText = ButtonConstants.CLAIM_TOTEM,
        confirmButtonEnabled = backpackHasEmptySpace,
        onConfirmButtonClick = {
            onClaim()
            onDismissRequest()
        },
        clickable = true,
    )
}