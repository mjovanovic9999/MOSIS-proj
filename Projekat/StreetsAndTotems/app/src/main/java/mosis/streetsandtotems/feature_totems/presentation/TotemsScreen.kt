package mosis.streetsandtotems.feature_totems.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_totems.presentation.components.TotemListItem
import mosis.streetsandtotems.ui.theme.sizes

@MainNavGraph
@Destination
@Composable
fun TotemsScreen(viewModel: TotemsViewModel) {
    val state = viewModel.totemsState.value
    val scrollState = rememberLazyListState()
    CustomPage(
        contentMaxWidth = MaterialTheme.sizes.lazy_column_max_width,
        contentVerticalArrangement = Arrangement.Top,
        titleContent = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                contentDescription = ImageContentDescriptionConstants.TOTEM,
                modifier = Modifier
                    .fillMaxHeight(MaterialTheme.sizes.totems_tiki_height)
                    .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = MaterialTheme.sizes.lazy_column_spacing)
                    .drawVerticalScrollbar(scrollState),
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.totems) {
                    Box(modifier = Modifier.padding(bottom = MaterialTheme.sizes.lazy_column_spacing))
                    {
                        TotemListItem(totem = it)
                    }
                }
            }
        })
}