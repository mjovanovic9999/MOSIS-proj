package mosis.streetsandtotems

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import ovh.plrapps.mapcompose.ui.gestures.detectTapGestures
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.ui.theme.AppTheme
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.api.onTap
import ovh.plrapps.mapcompose.ui.MapUI

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Column {
                    MapContainer(
                        Modifier
                            .size(400.dp, 400.dp)
                    )
                }

            }
        }
    }
}


@Composable
fun MapContainer(
    modifier: Modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
    viewModel: TestViewModel = hiltViewModel()
) {
    val myContext = LocalContext.current
    MapUI(
        modifier, state = viewModel.state
    )
    viewModel.state.onTap { x, y ->
        /*Toast
            .makeText(myContext, x.toString(), Toast.LENGTH_SHORT)
            .show()*/
        viewModel.state.addMarker((x + y).toString(), x, y, c = { Pin() }, clipShape = null)
    }
}

@Composable
fun Pin() {
    Image(
        painter = painterResource(R.drawable.pin_friend),
        contentDescription = null,
    )

}