package mosis.streetsandtotems.feature_map.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.R
import mosis.streetsandtotems.feature_map.domain.util.returnPinTypeResourceId
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomPinImage(imageUri: String) {
    val resourceId = R.drawable.pin_other_player
    Box {
        GlideImage(
            imageModel = imageUri,
            modifier = Modifier
                .height(31.dp)
                .width(31.dp)
                .offset(3.dp, 3.dp)
        )
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier = Modifier.height(48.dp),

            )
    }
}