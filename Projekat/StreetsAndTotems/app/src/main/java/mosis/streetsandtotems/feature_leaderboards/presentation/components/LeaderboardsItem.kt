package mosis.streetsandtotems.feature_leaderboards.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import mosis.streetsandtotems.core.LeaderboardItemsConstants
import mosis.streetsandtotems.core.presentation.components.CustomLazyColumnItem
import mosis.streetsandtotems.feature_leaderboards.domain.model.LeaderboardUser
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun LeaderboardsItem(index: Int, leaderboardUser: LeaderboardUser) {
    CustomLazyColumnItem {
        Text(
            text = "$index.",
            modifier = Modifier.weight(MaterialTheme.sizes.leaderboard_position_weight),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(MaterialTheme.sizes.leaderboard_username_weight)
        ) {
            Text(
                text = LeaderboardItemsConstants.USERNAME,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = leaderboardUser.username, style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(MaterialTheme.sizes.leaderboard_points_weight)
        ) {
            Text(
                text = LeaderboardItemsConstants.POINTS,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = leaderboardUser.points.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}