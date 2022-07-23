package mosis.streetsandtotems

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.ui.theme.AppTheme
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.ui.MapUI

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MapContainer()
            }
        }
    }
}


@Composable
fun MapContainer(
    modifier: Modifier = Modifier.fillMaxHeight().fillMaxWidth(), viewModel: TestViewModel = hiltViewModel()
) {

    MapUI(modifier, state = viewModel.state)

    Button(onClick = {
        viewModel.state.addMarker("id", x = 0.5, y = 0.5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )
        }
    }) {
        Text(text = "Simple Button")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
    }
}