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
fun CustomPinImage(imageUri: String, isMyFriend: Boolean) {
    Box {
        GlideImage(
            imageModel = imageUri,
            modifier = Modifier
                .height(31.dp)
                .width(31.dp)
                .offset(3.dp, 3.dp)
        )
        Image(
            painter = painterResource(if (isMyFriend) R.drawable.pin_friend else R.drawable.pin_other_player),
            contentDescription = null,
            modifier = Modifier.height(48.dp),
            )
    }
}