package com.example.shopclothes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class InternetCheckReceiver(
    private val callback: (Boolean)->Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        callback.invoke(isNetworkAvailable(context))
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.allNetworks.forEach { network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let {
                val isVPN = it.hasCapability(NetworkCapabilities.TRANSPORT_VPN)
                if(isVPN.not()){
                    return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
            }
        }
        return false
    }
}