package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomPin(pinId: String, resourceId: Int) {
    Image(
        painter = painterResource(resourceId),
        contentDescription = null,
        modifier = Modifier.height(48.dp),
    )

}
