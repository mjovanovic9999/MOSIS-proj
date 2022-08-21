package mosis.streetsandtotems.core.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.ImageContentDescriptionConstants

@RootNavGraph
@Destination
@Composable
fun TikiScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Red, CircleShape)
                    .fillMaxWidth()
                    .aspectRatio(0.8f, true)
            ) {
                Text(
                    text = "Verification link sent to your email address lorem",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
//            Box(modifier = Modifier.background(Color.Red))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.tiki),
                contentDescription = ImageContentDescriptionConstants.TOTEM,
            )
        }
    }
}