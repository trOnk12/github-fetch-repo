package com.example.brightinventions.domain.repository

import com.example.brightinventions.domain.model.NetworkState

interface INetworkStateRepository {
    fun getNetworkState(): NetworkState
}