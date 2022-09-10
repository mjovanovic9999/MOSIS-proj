package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomMarketDialog(
//vrv treba scroll
    isOpen: Boolean = true,
    onDismissRequest: () -> Unit = {},
) {
    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = onDismissRequest,
        title = {
            CustomAreaHelper(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                cell00 = {
                    Text(
                        fontSize = 20.sp,
                        style = TextStyle.Default.copy(fontWeight = FontWeight(800)),
                        text = "MARKET "
                    )
                },
            )
        },
        text = {
            Column {
                StoneMarket(itemsLeft = 5, emptySpaces = 2)
                //sledeci
            }
        },
        buttonType = CustomButtonType.Outlined,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = false,
        dismissButtonVisible = false,
        clickable = true,
    )


}


@Composable
private fun StoneMarket(
    itemsLeft: Int?,
    emptySpaces: Int?,

    ) {
    CustomAreaHelper(
        modifier = Modifier
            .fillMaxWidth()
            .size(150.dp),
        shouldAlignBottom = true,
        topPadding = true,
        title = {
            CustomDialogTitle(
                isTotem = false,
                resourceType = IconType.ResourceType.Stone,
                countMessage = itemsLeft.toString() + TitleConstants.ITEMS_LEFT,
                backpackSpaceMessage = emptySpaces.toString() + TitleConstants.BACKPACK_EMPTY_SPACES_LEFT,
                needTotemAdditionalText = false,
            )
        },
        cell10 = {
            CustomButton(
                buttonModifier = Modifier.padding(5.dp),
                clickHandler = { },
                text = ButtonConstants.BUY,
                contentPadding = ButtonDefaults.ContentPadding,
                buttonType = CustomButtonType.Outlined,
                textStyle = MaterialTheme.typography.titleMedium,

                )
        },
        cell11 = {
            CustomTextField(
                modifier = Modifier
                    .height(MaterialTheme.sizes.drop_item_dialog_amount_text_field_height)
                    .padding(
                        start = MaterialTheme.sizes.drop_item_dialog_spacer,
                        end = 5.dp
                    ),
                value = "2",
                onValueChange = {},
//                        singleLine = true,
                textFieldType = CustomTextFieldType.Outlined,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                placeholder = FormFieldConstants.AMOUNT,
                label = FormFieldConstants.AMOUNT,

                )
        },
    )
}