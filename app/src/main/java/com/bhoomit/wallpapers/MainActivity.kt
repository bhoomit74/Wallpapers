package com.bhoomit.wallpapers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bhoomit.wallpapers.util.CheckConnection

class MainActivity : AppCompatActivity() {

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavController = findNavController(R.id.fragmentContainer)


        Handler(Looper.getMainLooper()).postDelayed({
            initObserver()
        },2100)
    }

    private fun initObserver(){
        CheckConnection(applicationContext).observe(this, Observer {
            if (it && mNavController.currentDestination?.id == R.id.noInternetFragment){
                mNavController.navigate(R.id.action_noInternetFragment_to_dashboardFragment)
            }
            else if (!it && mNavController.currentDestination?.id == R.id.dashboardFragment){
                mNavController.navigate(R.id.action_dashboardFragment_to_noInternetFragment)
            }

        })
    }
}