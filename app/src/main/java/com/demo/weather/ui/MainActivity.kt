package com.demo.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.weather.R
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.listener.IFragmentCommunicator
import com.demo.weather.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_app_bar_short.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IFragmentCommunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_main)


        this.setSupportActionBar(topAppBar)


        initFragment(savedInstanceState)
    }


    private fun initFragment(savedInstanceState: Bundle?) {

        if (isTwoLayoutPan().not()) {


            savedInstanceState?.let { return } // For if already added


            val headlineFragment = HomeFragment()


            headlineFragment.arguments = intent.extras


            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, headlineFragment)
                .commit()
        }
    }


    private fun isTwoLayoutPan(): Boolean {

        layout_two_pan?.let {
            return true
        }

        return false
    }


    override fun onShowForecast(city: CityInfo) {

    }
}