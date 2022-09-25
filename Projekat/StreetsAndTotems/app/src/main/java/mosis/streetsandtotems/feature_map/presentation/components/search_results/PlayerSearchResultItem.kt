package mosis.streetsandtotems.feature_map.presentation.components.search_results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.core.LeaderboardItemsConstants
import mosis.streetsandtotems.core.presentation.components.CustomLazyColumnItem
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.ui.theme.sizes

class PlayerSearchResultItem(
    private val userData: ProfileData,
    private val onClick: (ProfileData) -> Unit
) :
    SearchResultItem {
    @Composable
    override fun getLazyColumnItem() {
        userData.user_name?.let {
            CustomLazyColumnItem(
                onButtonClick = { onClick(userData) }, buttonIcon = Icons.Filled.PersonPinCircle
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.9f)
                        .height(50.dp)
                        .padding(start = 5.dp)
                ) {
                    GlideImage(
                        imageModel = userData.image_uri,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
                            .fillMaxHeight(0.8f)
                            .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
                            .align(Alignment.CenterStart)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = LeaderboardItemsConstants.USERNAME,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = userData.user_name,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }

}
