package com.example.modelstoreproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

open class NetworkUtils {

    /**
     * check whether Internet is available or not
     *
     * @return true if internet is available else false
     */
   open fun isNetworkAvailable(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                if (nc != null) {
                    return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                }
            }
        }
        return false
    }
}