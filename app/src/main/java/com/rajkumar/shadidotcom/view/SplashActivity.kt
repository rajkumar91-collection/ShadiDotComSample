package com.rajkumar.shadidotcom.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rajkumar.shadidotcom.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this,HomeScreenActivity::class.java))
            finish()
        },5000)


    }
}