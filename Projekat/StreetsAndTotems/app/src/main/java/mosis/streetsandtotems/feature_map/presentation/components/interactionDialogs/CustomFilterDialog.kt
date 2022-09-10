package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.PinConstants.FRIENDS
import mosis.streetsandtotems.core.PinConstants.RESOURCES
import mosis.streetsandtotems.core.PinConstants.TOTEMS
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.feature_map.presentation.components.CustomFilterChip

@Composable
fun CustomFilterDialog(
    isOpen: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    updateFilterResources: () -> Unit,
    updateFilterPlayers: () -> Unit,
    updateFilterTotems: () -> Unit,
    filterResourceState: Boolean,
    filterFriendsState: Boolean,
    filterTotemsState: Boolean,
) {
    CustomDialog(
        modifier = Modifier.fillMaxWidth(),
        isOpen = isOpen,
        title = {
            Text("Visible pins")
        },
        text = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                item {
                    CustomFilterChip(
                        !filterResourceState,
                        { updateFilterResources() },
                        RESOURCES
                    )
                }
                item {
                    CustomFilterChip(
                        !filterFriendsState,
                        { updateFilterPlayers() },
                        FRIENDS
                    )
                }
                item {
                    CustomFilterChip(
                        !filterTotemsState,
                        { updateFilterTotems() },
                        TOTEMS
                    )
                }
            }
//            FlowRow(mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly) {
//
//
//
//            }

        },
        confirmButtonEnabled = true,
        confirmButtonText = ButtonConstants.APPLY,
        onConfirmButtonClick = { onConfirmButtonClick() },
        dismissButtonEnabled = true,
        dismissButtonText = ButtonConstants.DISMISS,
        onDismissButtonClick = { onDismissRequest() },
    )


}