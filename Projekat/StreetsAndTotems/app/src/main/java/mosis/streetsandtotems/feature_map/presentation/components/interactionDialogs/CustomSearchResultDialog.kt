package mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.core.ButtonConstants
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomButtonType
import mosis.streetsandtotems.core.presentation.components.CustomDialog
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_map.presentation.components.search_results.SearchResultItem
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomSearchResultDialog(
    isOpen: Boolean, onDismissRequest: () -> Unit, searchResults: List<SearchResultItem>
) {
    val scrollState = rememberLazyListState()
    CustomDialog(
        isOpen = isOpen,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = TitleConstants.SEARCH_RESULTS,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        text = {
            if (searchResults.isEmpty())
                Text(
                    text = MessageConstants.NO_RESULTS,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge
                )
            else
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 300.dp)
                    .padding(
                        bottom = MaterialTheme.sizes.lazy_column_spacing
                    )
                    .drawVerticalScrollbar(scrollState),
                    state = scrollState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        searchResults.forEach {
                            item { it.getLazyColumnItem() }
                        }
                    })
        },
        confirmButtonVisible = false,
        onDismissButtonClick = onDismissRequest,
        dismissButtonText = ButtonConstants.DISMISS,
        dismissButtonMatchParentWidth = true,
        buttonType = CustomButtonType.Outlined
    )
}
