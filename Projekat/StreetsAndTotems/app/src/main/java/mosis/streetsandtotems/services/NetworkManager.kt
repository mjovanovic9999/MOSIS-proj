package mosis.streetsandtotems.services

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.mutableStateOf

class NetworkManager(var context: Application) {

    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    private var callback: ConnectivityManager.NetworkCallback = object :
        ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            isNetworkConnectivityValid.value = false
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            isNetworkConnectivityValid.value =
                connectivityManager.getNetworkCapabilities(network)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true
        }
    }

    init {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    companion object {
        var isNetworkConnectivityValid =
            mutableStateOf(false)
    }
}