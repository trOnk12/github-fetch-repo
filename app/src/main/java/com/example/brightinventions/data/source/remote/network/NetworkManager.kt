package com.example.brightinventions.data.source.remote.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class NetworkManager @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun getNetworkState(): NetworkState {
        connectivityManager.activeNetwork?.let { activeNetwork ->
            connectivityManager.getNetworkCapabilities(activeNetwork)?.let { networkCapability ->
                if (isDeviceOnline(networkCapability)) {
                    return NetworkState.Online
                }
            }
        }
        return NetworkState.Offline
    }

    private fun isDeviceOnline(networkCapability: NetworkCapabilities): Boolean {
        return networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapability.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        )
    }
}

sealed class NetworkState {
    object Online : NetworkState()
    object Offline : NetworkState()
}