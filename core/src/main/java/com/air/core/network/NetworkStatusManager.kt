package com.air.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class NetworkStatusManager @Inject constructor(
    private val context: Context
) {

    val isConnected: Boolean
        get() = _isConnected.get()

    private var _isConnected: AtomicBoolean = AtomicBoolean(false)

    init {
        listenNetworkChanges()
    }

    private fun listenNetworkChanges() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isConnected = AtomicBoolean(true)
            }

            override fun onLost(network: Network) {
                _isConnected = AtomicBoolean(false)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            connectivityManager.registerNetworkCallback(
                request,
                networkCallback
            )
        }
    }
}