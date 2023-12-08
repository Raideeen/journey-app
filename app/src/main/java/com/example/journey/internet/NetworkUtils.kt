package com.example.journey.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility object for network-related operations.
 */
object NetworkUtils {

    /**
     * Checks if a network connection is available.
     *
     * This method checks if there is an active network and if it has either a WIFI or CELLULAR transport.
     * It uses the ConnectivityManager to get information about the active network.
     *
     * @param context The context used to get the ConnectivityManager system service.
     * @return True if a network connection is available, false otherwise.
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}