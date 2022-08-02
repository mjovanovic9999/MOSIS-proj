package mosis.streetsandtotems.feature_map.presentation.map.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun FAB(resourceId: Int, onClick: () -> Unit={}, modifier: Modifier = Modifier) {

    FloatingActionButton(onClick = onClick, Modifier.padding(7.dp)) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier.height(40.dp)
        )

    }
}