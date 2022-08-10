package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun CustomFAB(resourceId: Int, onClick: () -> Unit={}, modifier: Modifier = Modifier) {

    FloatingActionButton(onClick = onClick, Modifier.padding(MaterialTheme.sizes.fab_padding)) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier.height(MaterialTheme.sizes.fab_image_height)
        )

    }
}