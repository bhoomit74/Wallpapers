package com.bhoomit.wallpapers.util

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object CheckInternetBySocketConnection {

    /**
     * Send a ping to google's primary DNS.
     * If successful, that means we have internet.
     */
    fun execute(socketFactory: SocketFactory): Boolean {
        return try{
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            true
        }catch (e: IOException){
            false
        }
    }

}