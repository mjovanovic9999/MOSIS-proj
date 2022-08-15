package mosis.streetsandtotems.services

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf

class NetworkManager(var context: Application) {

    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)


    ///ne poziva se inicijalno   ako je wifi ugasen

    private var callback: ConnectivityManager.NetworkCallback = object :
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
            Toast.makeText(context, "NEtworkkkkk", Toast.LENGTH_SHORT).show()
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
            mutableStateOf(false)//ako nece da registruje inicijalno ugasenu mrezu
    }
}