package mosis.streetsandtotems.core.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.options
import com.skydoves.landscapist.glide.GlideImage
import mosis.streetsandtotems.core.FormFieldConstants
import mosis.streetsandtotems.ui.theme.sizes


@Composable
fun CustomImageSelectorAndCropper(
    focusRequester: FocusRequester? = null,
    onImageSelected: ((String) -> Unit)? = null,
    showErrorSnackbar: ((String) -> Unit)? = null,
    initialImageUri: Uri? = null,
    modifier: Modifier = Modifier.size(MaterialTheme.sizes.image_select_size),
    selectEnabled: Boolean = true
) {
    val context = LocalContext.current
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri.value = result.uriContent
            val imagePath = result.getUriFilePath(context)
            imagePath?.let {
                onImageSelected?.invoke(imagePath)
//                val imageBytes = File(imagePath).readBytes()
//                val base64 = Base64.getEncoder().encodeToString(imageBytes)
//                onImageSelected?.invoke(Uri.parse(ConversionConstants.BASE64_IMAGE_PREFIX + base64))
            }
        } else {
            showErrorSnackbar?.invoke(result.error?.message.toString())
        }
    }

    var boxModifier = modifier
        .background(
            MaterialTheme.colorScheme.secondaryContainer,
            RoundedCornerShape(MaterialTheme.sizes.default_shape_corner)
        )


    if (selectEnabled)
        boxModifier = boxModifier.clickable {
            imageCropLauncher.launch(options {
                setImageSource(includeGallery = true, includeCamera = true)
                setAspectRatio(1, 1)
            })
        }

    Box(
        modifier = if (focusRequester != null) boxModifier
            .focusable()
            .focusRequester(focusRequester) else boxModifier
    ) {
        if (imageUri.value != null || initialImageUri != null) {
            GlideImage(
                imageModel = imageUri.value ?: initialImageUri,
                modifier = Modifier.clip(RoundedCornerShape(MaterialTheme.sizes.default_shape_corner))
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
}

