package com.example.brightinventions.data.repository

import android.net.ConnectivityManager
import com.example.brightinventions.domain.model.NetworkState
import com.example.brightinventions.domain.repository.INetworkStateRepository
import javax.inject.Inject

class NetworkStateRepository
@Inject constructor(
    private val connectivityManager: ConnectivityManager
) : INetworkStateRepository {

    override fun getNetworkState(): NetworkState {
        if (connectivityManager.activeNetwork == null) {
            return NetworkState.Online
        }
        return NetworkState.Offline
    }

}