package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.PinConstants.MARKET
import mosis.streetsandtotems.core.PinConstants.PINS
import mosis.streetsandtotems.core.PinConstants.PLAYERS
import mosis.streetsandtotems.core.PinConstants.RESOURCES
import mosis.streetsandtotems.core.PinConstants.TOTEMS
import mosis.streetsandtotems.core.PinConstants.VISIBLE_PINS
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
    updateFilterCustomPins: () -> Unit,
    updateFilterMarket: () -> Unit,
    filterResourceState: Boolean,
    filterFriendsState: Boolean,
    filterTotemsState: Boolean,
    filterCustomPinsState: Boolean,
    filterMarketState: Boolean,
) {
    CustomDialog(
        modifier = Modifier.fillMaxWidth(),
        isOpen = isOpen,
        title = {
            Text(VISIBLE_PINS)
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
                        PLAYERS
                    )
                }
                item {
                    CustomFilterChip(
                        !filterTotemsState,
                        { updateFilterTotems() },
                        TOTEMS
                    )
                }
                item {
                    CustomFilterChip(
                        !filterCustomPinsState,
                        { updateFilterCustomPins() },
                        PINS
                    )
                }
                item {
                    CustomFilterChip(
                        !filterMarketState,
                        { updateFilterMarket() },
                        MARKET
                    )
                }
            }
        },
        confirmButtonEnabled = true,
        confirmButtonText = ButtonConstants.APPLY,
        onConfirmButtonClick = { onConfirmButtonClick() },
        dismissButtonEnabled = true,
        dismissButtonText = ButtonConstants.DISMISS,
        onDismissButtonClick = onDismissRequest,
        onDismissRequest = onDismissRequest
    )


}