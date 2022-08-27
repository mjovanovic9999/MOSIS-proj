package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mosis.streetsandtotems.feature_map.domain.util.returnPinTypeResourceId

@Composable
fun CustomPin(pinId: String, modifier: Modifier = Modifier) {
    val resourceId = returnPinTypeResourceId(pinId)
    Image(
        painter = painterResource(resourceId),
        contentDescription = null,
        modifier = modifier.height(48.dp),
    )

}
