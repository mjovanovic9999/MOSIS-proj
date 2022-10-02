package mosis.streetsandtotems.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomFAB(
    imageVector: ImageVector,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    showAsImage: Boolean = false,
    contentDescription: String? = null,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    iconModifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor
    ) {
        if (showAsImage)
            Image(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = iconModifier
            )
        else
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = iconModifier
            )
    }
}