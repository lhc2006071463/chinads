package com.tiens.comonlibrary.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData

class NetworkLiveData(context: Context) : LiveData<NetworkInfo?>() {
    private val mContext: Context = context.applicationContext
    private val mNetworkReceiver: NetworkReceiver
    private val mIntentFilter: IntentFilter
    override fun onActive() {
        super.onActive()
        Log.d(TAG, "onActive:")
        mContext.registerReceiver(mNetworkReceiver, mIntentFilter)
    }

    override fun onInactive() {
        super.onInactive()
        Log.d(TAG, "onInactive: ")
        mContext.unregisterReceiver(mNetworkReceiver)
    }

    private class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val manager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = manager.activeNetworkInfo
            getInstance(context)!!.value = activeNetwork
        }
    }

    companion object {
        var mNetworkLiveData: NetworkLiveData? = null
        private const val TAG = "NetworkLiveData"
        fun getInstance(context: Context): NetworkLiveData? {
            if (mNetworkLiveData == null) {
                mNetworkLiveData = NetworkLiveData(context)
            }
            return mNetworkLiveData
        }
    }

    init {
        mNetworkReceiver = NetworkReceiver()
        mIntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }
}