package com.bhoomit.wallpapers.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckConnection(context : Context) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {

        //remove deprecated method and unusual condition
        if (manager.activeNetworkInfo!=null && manager.activeNetworkInfo!!.isConnected) {
            networkCallback = createNetworkCallback()
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NET_CAPABILITY_INTERNET)
                .build()
            manager.registerNetworkCallback(networkRequest, networkCallback)
        }
        else {
            checkValidNetworks()
        }
    }

    override fun onInactive() {
        manager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            val networkCapabilities = manager.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            if (hasInternetCapability == true) {
                //To check if this network actually has internet
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = CheckInternetBySocketConnection.execute(network.socketFactory)
                    if(hasInternet){
                        withContext(Dispatchers.Main){
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            validNetworks.remove(network)  //To remove network from the valid network list
            checkValidNetworks() // To check any valid network available
        }

    }

}
