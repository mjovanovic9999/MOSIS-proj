package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapFABs(
    drawerState: DrawerState,
    locateMe: () -> Unit,
    followMe: Boolean,
    showFilterDialog: () -> Unit,

    ) {
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        CustomFAB(
            imageVector = ImageVector.vectorResource(id = R.drawable.menu),
            onClick = { scope.launch { drawerState.open() } },
            modifier = Modifier
                .padding(MaterialTheme.sizes.fab_padding)
                .align(Alignment.TopStart)
        )
        CustomFAB(
            imageVector = ImageVector.vectorResource(id = R.drawable.layers),
            onClick = {
                showFilterDialog()
            },
            modifier = Modifier
                .padding(MaterialTheme.sizes.fab_padding)
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            if (!followMe) {
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.locate_me),
                    onClick = { locateMe() },
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )
            } else {
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.located_me),
                    modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
                )
            }
        }
    }
}