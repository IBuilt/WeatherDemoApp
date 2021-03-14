package com.demo.weather.ui.other

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.demo.weather.R
import com.demo.weather.ui.MainActivity

@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_splash)

        showSearchImageActivityWithDelay()
    }


    private fun showSearchImageActivityWithDelay() {

        Handler().postDelayed({
            showSearchImageActivity()
        }, 300)
    }


    private fun showSearchImageActivity() {

        this.startActivity(Intent(this, MainActivity::class.java))

        this.finish()
    }
}