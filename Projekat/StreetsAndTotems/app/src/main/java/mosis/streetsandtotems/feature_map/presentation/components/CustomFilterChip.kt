package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFilterChip(selected: Boolean, updateFilter: () -> Unit, labelText: String) {
    FilterChip(
        selected = selected,
        onClick = { updateFilter() },
        label = { Text(labelText) },
        leadingIcon = {
            if (selected)
                Icon(
                    Icons.Rounded.Done,
                    contentDescription = null
                )
        },
//        trailingIcon = { Pin(resourceId = R.drawable.tiki) },
    )
}