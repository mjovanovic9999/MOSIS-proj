package mosis.streetsandtotems.core.presentation.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val locationBroadcastReceiver: LocationBroadcastReceiver,
    val notificationProvider: NotificationProvider
) : ViewModel() {

}