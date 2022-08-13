package mosis.streetsandtotems.core.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.options
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.ui.theme.sizes


@Composable
fun CustomImageSelectorAndCropper() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
        } else {
            val exception = result.error
        }
    }

    Box(
        modifier = Modifier
            .size(MaterialTheme.sizes.image_select_size)
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(MaterialTheme.sizes.image_corner)
            )
            .clickable {
                imageCropLauncher.launch(options {
                    setImageSource(includeGallery = true, includeCamera = true)
                    setAspectRatio(1, 1)
                })
            }
    ) {
        if (imageUri != null) {
            GlideImage(
                imageModel = imageUri,
                modifier = Modifier.clip(RoundedCornerShape(MaterialTheme.sizes.image_corner))
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = FormFieldConstants.PROFILE_PHOTO,
                    style = MaterialTheme.typography.labelMedium.plus(
                        TextStyle(MaterialTheme.colorScheme.onSurface),
                    ),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.AddAPhoto,
                    contentDescription = FormFieldConstants.PROFILE_PHOTO,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(MaterialTheme.sizes.add_photo_icon)
                )
            }
        }
    }
    if (imageUri != null) {
        Text(
            text = FormFieldConstants.CHANGE_PHOTO,
            style = MaterialTheme.typography.labelSmall.plus(
                TextStyle(MaterialTheme.colorScheme.primary)
            )
        )
    }
}

