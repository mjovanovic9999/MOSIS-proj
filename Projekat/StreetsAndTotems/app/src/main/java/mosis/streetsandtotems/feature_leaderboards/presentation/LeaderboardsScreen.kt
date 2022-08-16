package mosis.streetsandtotems.feature_leaderboards.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.TitleConstants
import mosis.streetsandtotems.core.presentation.components.CustomPage
import mosis.streetsandtotems.core.presentation.utils.drawVerticalScrollbar
import mosis.streetsandtotems.feature_leaderboards.presentation.components.LeaderboardsItem
import mosis.streetsandtotems.ui.theme.sizes

@RootNavGraph
@Destination
@Composable
fun LeaderboardsScreen(viewModel: LeaderboardsViewModel) {
    val state = viewModel.leaderboardState.value
    val scrollState = rememberLazyListState()

    CustomPage(
        contentMaxWidth = MaterialTheme.sizes.lazy_column_max_width,
        contentVerticalArrangement = Arrangement.Top,
        titleText = TitleConstants.LEADERBOARD,
        titleContent = {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_text),
                contentDescription = ImageContentDescriptionConstants.LOGO_TEXT,
                modifier = Modifier
                    .fillMaxHeight(MaterialTheme.sizes.lazy_column_title_image_height)
                    .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
            )
        }, content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = MaterialTheme.sizes.lazy_column_spacing)
                    .drawVerticalScrollbar(scrollState),
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(state.leaderboardUsers) { index, item ->
                    LeaderboardsItem(index = index + 1, leaderboardUser = item)
                }
            }
        })
}