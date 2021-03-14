package com.demo.lib.utils

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun LatLng.getAddressAsync(activity: AppCompatActivity) =

    activity.lifecycleScope.async(Dispatchers.IO) {

        Geocoder(activity).getFromLocation(
            this@getAddressAsync.latitude, this@getAddressAsync.longitude, 2
        )
    }


