package mosis.streetsandtotems.feature_map.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.presentation.components.CustomFAB
import mosis.streetsandtotems.ui.theme.sizes

@Composable
fun MapFABs(
    openDrawer: () -> Unit,
    locateMe: () -> Unit,
    followMe: Boolean,
    showFilterDialog: () -> Unit,
    showSearchDialog: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomFAB(
            imageVector = ImageVector.vectorResource(id = R.drawable.menu),
            onClick = { openDrawer() },
            modifier = Modifier
                .padding(MaterialTheme.sizes.fab_padding)
                .align(Alignment.TopStart)
        )
        Column(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            CustomFAB(
                imageVector = ImageVector.vectorResource(id = R.drawable.layers), onClick = {
                    showFilterDialog()
                }, modifier = Modifier.padding(MaterialTheme.sizes.fab_padding)
            )
            CustomFAB(
                imageVector = Icons.Outlined.Search,
                modifier = Modifier.padding(MaterialTheme.sizes.fab_padding),
                onClick = showSearchDialog,
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxSize(),
        ) {
            if (!followMe) {
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.locate_me),
                    onClick = { locateMe() },
                    modifier = Modifier
                        .padding(MaterialTheme.sizes.fab_padding),
                )
            } else {
                CustomFAB(
                    imageVector = ImageVector.vectorResource(id = R.drawable.located_me),
                    modifier = Modifier
                        .padding(MaterialTheme.sizes.fab_padding),
                )
            }
        }

    }
}