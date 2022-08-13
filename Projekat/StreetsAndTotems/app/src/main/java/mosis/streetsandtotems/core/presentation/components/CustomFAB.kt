package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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