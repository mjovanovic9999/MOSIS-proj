package mosis.streetsandtotems.services

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.mutableStateOf

class NetworkManager(var context: Application) {

    init {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                Log.d(
                    "tag",
                    "The application no longer has a default network. The last default network was " + network
                )
                isNetworkConnectivityValid.value = false

            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                Log.d(
                    "tag",
                    "caps " + connectivityManager.getNetworkCapabilities(network)
                        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED).toString()
                )
                isNetworkConnectivityValid.value =
                    connectivityManager.getNetworkCapabilities(network)
                        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true

                Log.d(
                    "tag",
                    "caps var" + isNetworkConnectivityValid.toString()
                )
            }
        })
    }

    fun ok(){}

    companion object {
        var isNetworkConnectivityValid = mutableStateOf(true)
    }
}