package mosis.streetsandtotems.feature_main.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mosis.streetsandtotems.core.DrawerConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.CustomSelect
import mosis.streetsandtotems.core.presentation.components.CustomTextFieldType
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun DrawerIconSelection(label: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = ImageContentDescriptionConstants.BULLET,
                modifier = Modifier.size(MaterialTheme.sizes.drawer_bullet_size),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.sizes.drawer_spacer))
            CustomSelect(
                selectList = DrawerConstants.DROPDOWN_SELECT_LIST,
                label = label,
                modifier = Modifier.fillMaxWidth(),
                type = CustomTextFieldType.Outlined,
                readOnly = true,
                defaultSelectedIndex = 0,
                height = MaterialTheme.sizes.drawer_select_height
            )
        }
    }
}