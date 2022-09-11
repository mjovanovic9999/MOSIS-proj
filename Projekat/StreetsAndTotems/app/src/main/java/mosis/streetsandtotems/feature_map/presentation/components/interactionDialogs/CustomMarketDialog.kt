package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.core.presentation.components.CustomDialogTitle
import mosis.streetsandtotems.core.presentation.components.CustomTextField
import mosis.streetsandtotems.feature_map.presentation.util.convertResourceTypeToIconType
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomMarkerDialog(//vrv treba scroll
    isOpen: Boolean = true,

    ) {

    CustomDialog(
        isOpen = isOpen,
        modifier = Modifier
            .fillMaxWidth(MaterialTheme.sizes.drop_item_dialog_width),
        onDismissRequest = { },
        title = {
            CustomAreaHelper(

                modifier = Modifier.fillMaxWidth().size(55.dp),
                row00 = {

                    Text(
                        fontSize=20.sp,
                        style = TextStyle.Default.copy(fontWeight = FontWeight(800)),
                        text = "MARKET "
                    )

                },
            )
        },

        text = {

        },
        buttonType = CustomButtonType.Outlined,
        confirmButtonMatchParentWidth = true,
        confirmButtonVisible = false,
        dismissButtonVisible = false,
        clickable = true,
    )


}