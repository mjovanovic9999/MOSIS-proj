package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.feature_map.domain.util.PinTypes
import mosis.streetsandtotems.services.LocationService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomFilterDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    filterShowTikis: Boolean,
    changeFilterTikis: () -> Unit,
    filterShowFriends: Boolean,
    changeFilterFriends: () -> Unit,
    filterShowResources: Boolean,
    changeFilterResources: () -> Unit,
    applyFilters: () -> Unit,
) {
    val commentState = remember { mutableStateListOf<PinTypes>() }
    val isSelected = remember {
        mutableStateOf(false)
    }

    CustomDialog(
        modifier = Modifier.fillMaxWidth(),
        isOpen = isOpen,
        title = {
            Text("Filter map pins")
        },
        text = {
            Column {
                Text(text = "Category")
                Row {
                    FilterChip(
                        selected = filterShowTikis,
                        onClick = { changeFilterTikis() },
                        label = { Text(text = "Tikis") },
                        leadingIcon = {
                            if (filterShowTikis)
                                Icon(
                                    Icons.Rounded.Done,
                                    contentDescription = null
                                )
                        },
//                        trailingIcon = { Pin(resourceId = R.drawable.tiki) },
                    )
                    FilterChip(
                        selected = filterShowFriends,
                        onClick = { changeFilterFriends() },
                        label = { Text(text = "Friends") },
                        leadingIcon = {
                            if (filterShowFriends)
                                Icon(
                                    Icons.Rounded.Done,
                                    contentDescription = null
                                )
                        },
                    )
                    FilterChip(
                        selected = filterShowResources,
                        onClick = { changeFilterResources() },
                        label = { Text(text = "Resources") },
                        leadingIcon = {
                            if (filterShowResources)
                                Icon(
                                    Icons.Rounded.Done,
                                    contentDescription = null
                                )
                        },
                    )
                }
                Divider()
            }
        },
        confirmButtonEnabled = true,
        confirmButtonText = "Apply filter",
        onConfirmButtonClick = { applyFilters() },
        dismissButtonEnabled = true,
        dismissButtonText = "Dismiss",
        onDismissButtonClick = { onDismissRequest() },
    )


}