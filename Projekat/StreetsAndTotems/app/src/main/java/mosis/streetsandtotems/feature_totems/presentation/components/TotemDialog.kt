package mosis.streetsandtotems.feature_totems.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.TotemDialogConstants
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.feature_totems.presentation.TotemsState
import mosis.streetsandtotems.ui.theme.sizes
import java.time.format.DateTimeFormatter
@Composable
fun TotemDialog(state: TotemsState, onDismissRequest: () -> Unit) {
    CustomDialog(
        isOpen = state.dialogOpen,
        onDismissRequest = onDismissRequest,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                    contentDescription = ImageContentDescriptionConstants.TOTEM,
                    modifier = Modifier.size(MaterialTheme.sizes.tiki_title_icon)
                )
                Text(
                    text = TitleConstants.MORE_INFORMATION,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = TotemDialogConstants.PLACING_TIME,
                        color = MaterialTheme.colorScheme.onSurfaceVariant

                    )
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = TotemDialogConstants.LAST_VISIT,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(modifier = Modifier.padding(start = MaterialTheme.sizes.totem_dialog_text_spacing)) {
                    if (state.selectedTotem != null) {
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = state.totems[state.selectedTotem].placingTime.format(
                                DateTimeFormatter.ofPattern(TotemDialogConstants.DATE_TIME_FORMAT)
                            )
                        )
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = state.totems[state.selectedTotem].lastVisited.format(
                                DateTimeFormatter.ofPattern(TotemDialogConstants.DATE_TIME_FORMAT)
                            )
                        )
                    }
                }
            }

        },
        confirmButtonText = ButtonConstants.SHOW_ON_MAP
    )
}