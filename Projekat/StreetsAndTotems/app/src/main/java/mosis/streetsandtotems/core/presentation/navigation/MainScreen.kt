package mosis.streetsandtotems.core.presentation.navigation

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.GroupRemove
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import mosis.streetsandtotems.NavGraphs
import mosis.streetsandtotems.core.DrawerConstants
import mosis.streetsandtotems.core.ImageContentDescriptionConstants
import mosis.streetsandtotems.core.presentation.components.*
import mosis.streetsandtotems.destinations.MapScreenDestination
import mosis.streetsandtotems.feature_map.domain.LocationDTO
import mosis.streetsandtotems.feature_map.presentation.MapViewModel
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission
import mosis.streetsandtotems.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    var myLocation = remember { mutableStateOf(LocationDTO(-1.0, -1.0, -1.0f)) }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(Modifier.align(Alignment.CenterHorizontally)) },
        content = { DrawerScreen(navController = navController, drawerState = drawerState) }
    )
    CustomRequestPermission(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    )
}

@Composable
private fun DrawerContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(MaterialTheme.sizes.drawer_column_width)
            .padding(top = MaterialTheme.sizes.drawer_spacing),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_spacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_spacer)
        ) {
            Column(
                modifier = Modifier.height(MaterialTheme.sizes.drawer_image_and_text_size),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.none)
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.sizes.drawer_image_size)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(MaterialTheme.sizes.image_corner)
                        )
                ) {
                }
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    buttonType = CustomButtonType.Text,
                    text = DrawerConstants.PROFILE
                )
            }
            Column(
                modifier = Modifier.height(MaterialTheme.sizes.drawer_image_size),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "KorisnickoIme",
                    style = MaterialTheme.typography.titleLarge.plus(TextStyle(fontWeight = FontWeight.ExtraBold)),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Petar Petrovic",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium.plus(TextStyle(fontWeight = FontWeight.Bold))
                )
            }

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerIconText(
            text = DrawerConstants.SETTINGS,
            icon = Icons.Outlined.Settings,
            contentDescriptions = ImageContentDescriptionConstants.SETTINGS,
            iconModifier = Modifier.size(MaterialTheme.sizes.drawer_icon_size),
            textStyle = MaterialTheme.typography.titleLarge.plus(
                TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerSwitchItem(
            text = DrawerConstants.NOTIFIACTIONS,
            switchState = false,
            onCheckedChangeHandler = {})
        DrawerSwitchItem(
            text = DrawerConstants.RUN_IN_BACKGROUND,
            switchState = true,
            onCheckedChangeHandler = {})
        DrawerSwitchItem(
            text = DrawerConstants.SHOW_PHONE_NUMBER,
            switchState = false,
            onCheckedChangeHandler = {})
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        DrawerIconSelection(label = DrawerConstants.CALL_SETTINGS)
        DrawerIconSelection(label = DrawerConstants.SMS_SETTINGS)
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = MaterialTheme.sizes.drawer_last_divider_top_padding)
        )
        CustomButton(
            clickHandler = { /*TODO*/ },
            text = DrawerConstants.LEADERBOARD,
            buttonType = CustomButtonType.Text,
            icon = Icons.Outlined.Leaderboard,
            iconSize = MaterialTheme.sizes.drawer_icon_size,
            textStyle = MaterialTheme.typography.titleMedium.plus(
                TextStyle(
                    fontWeight = FontWeight.Bold
                )
            ),
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = MaterialTheme.sizes.drawer_leave_buttons_spacing),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.sizes.drawer_leave_buttons_spacing),
                horizontalAlignment = Alignment.End
            ) {
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = DrawerConstants.LEAVE_SQUAD,
                    buttonType = CustomButtonType.Text,
                    icon = Icons.Outlined.GroupRemove,
                    iconPosition = IconPosition.End,
                    iconSize = MaterialTheme.sizes.icon,
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                )
                CustomButton(
                    clickHandler = { /*TODO*/ },
                    text = DrawerConstants.SIGN_OUT,
                    buttonType = CustomButtonType.Text,
                    icon = Icons.Outlined.Logout,
                    iconPosition = IconPosition.End,
                    iconSize = MaterialTheme.sizes.icon,
                    textStyle = MaterialTheme.typography.titleMedium.plus(
                        TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.tertiary)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScreen(navController: NavHostController, drawerState: DrawerState) {
    Scaffold(bottomBar = {
        BottomBar(
            navController = navController,
            navGrahp = NavGraphs.main,
            destinations = BottomBarDestinations.DefaultDestinations()
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.main,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(MapScreenDestination) { drawerState }
                    dependency(MapScreenDestination) { hiltViewModel<MapViewModel>() }
                })
        }
    }
}

@Composable
fun DrawerSwitchItem(
    text: String,
    switchState: Boolean,
    onCheckedChangeHandler: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DrawerIconText(
            text = text,
            icon = Icons.Filled.Circle,
            contentDescriptions = ImageContentDescriptionConstants.BULLET,
            iconModifier = Modifier.size(MaterialTheme.sizes.drawer_bullet_size)
        )
        Switch(
            checked = switchState,
            onCheckedChange = onCheckedChangeHandler,
            modifier = Modifier.scale(MaterialTheme.sizes.drawer_switch_scale_factor)
        )
    }
}

@Composable
fun DrawerIconText(
    text: String,
    icon: ImageVector,
    contentDescriptions: String,
    iconModifier: Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescriptions,
            modifier = iconModifier,
            tint = tint
        )
        Spacer(modifier = Modifier.width(MaterialTheme.sizes.drawer_spacer))
        Text(text = text, style = textStyle, color = tint)
    }
}

@Composable
fun DrawerIconSelection(label: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Circle,
                contentDescription = ImageContentDescriptionConstants.BULLET,
                modifier = Modifier.size(MaterialTheme.sizes.drawer_bullet_size),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.sizes.drawer_spacer))
            CustomSelect(
                selectList = DrawerConstants.DROPDOWN_SELECT_LIST,
                label = label,
                modifier = Modifier.fillMaxWidth(),
                type = CustomTextFieldType.Outlined,
                readOnly = true,
                defaultSelectedIndex = 0,
                height = MaterialTheme.sizes.drawer_select_height
            )
        }
    }
}