package mosis.streetsandtotems.feature_map.presentation.components.search_results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.SearchConstants
import mosis.streetsandtotems.core.SearchConstants.DISTANCE_UNIT
import mosis.streetsandtotems.core.SearchConstants.NULL_GEOPOINT
import mosis.streetsandtotems.core.presentation.components.CustomLazyColumnItem
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.presentation.util.distanceBetweenGeoPoints
import mosis.streetsandtotems.ui.theme.sizes

class ResourceSearchResultItem(
    private val resourceData: ResourceData,
    private val onClick: (ResourceData) -> Unit,
    private val myLocation: GeoPoint,
) : SearchResultItem {
    @Composable
    override fun getLazyColumnItem() {
        CustomLazyColumnItem(
            onButtonClick = { onClick(resourceData) },
            buttonIcon = Icons.Filled.Place
        ) {
            resourceData.type?.let {
                Box(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .background(
                            MaterialTheme.colorScheme.tertiaryContainer, CircleShape
                        )
                        .weight(0.175f)
                        .aspectRatio(MaterialTheme.sizes.default_aspect_ratio)
                ) {
                    Icon(
                        imageVector = when (it) {
                            ResourceType.Emerald -> ImageVector.vectorResource(id = R.drawable.emerald)
                            ResourceType.Wood -> ImageVector.vectorResource(id = R.drawable.wood)
                            ResourceType.Stone -> ImageVector.vectorResource(id = R.drawable.stone)
                            ResourceType.Brick -> ImageVector.vectorResource(id = R.drawable.brick)
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .size(MaterialTheme.sizes.drop_item_dialog_icon_size)
                            .padding(MaterialTheme.sizes.drop_item_dialog_icon_padding),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.35f)
                ) {
                    Text(
                        text = SearchConstants.TYPE,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.35f)
                ) {
                    Text(
                        text = SearchConstants.ITEM_DISTANCE,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text =
                        if (null == resourceData.l)
                            NULL_GEOPOINT
                        else
                            (distanceBetweenGeoPoints(
                                myLocation,
                                resourceData.l
                            ) + 0.5f).toInt().toString() + DISTANCE_UNIT,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}