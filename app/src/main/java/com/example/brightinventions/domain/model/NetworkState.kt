package com.example.brightinventions.domain.model

sealed class NetworkState{
    object Offline : NetworkState()
    object Online : NetworkState()
}