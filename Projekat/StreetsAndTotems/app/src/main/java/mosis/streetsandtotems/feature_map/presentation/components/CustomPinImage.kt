package mosis.streetsandtotems.feature_map.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.R

@Composable
fun CustomPinImage(imageUri: Uri, isMyFriend: Boolean) {
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