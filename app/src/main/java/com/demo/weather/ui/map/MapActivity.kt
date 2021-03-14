package com.demo.weather.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.demo.lib.utils.getAddressAsync
import com.demo.weather.R
import com.demo.weather.constants.Constant
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    private var lastKnownLocation: Location? = null


    private lateinit var currentLocationMarker: Marker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }


        setContentView(R.layout.activity_maps)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onMapReady(map: GoogleMap) {

        this.map = map


        getLocationPermission()


        updateLocationUI()


        getDeviceLocation()


        handleEvents()
    }


    private fun getDeviceLocation() {

        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationPermissionGranted = false

        when (requestCode) {

            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }


        updateLocationUI()
    }


    private fun createMarker(location: LatLng) {

        currentLocationMarker = map!!.addMarker(
            MarkerOptions()
                .position(LatLng(location.latitude, location.longitude))
                .draggable(true)
        )
    }


    private fun handleEvents() {

        map?.setOnCameraMoveListener {

            if (::currentLocationMarker.isInitialized)
                currentLocationMarker.remove()

            createMarker(map!!.cameraPosition.target)
        }


        map?.setOnMarkerClickListener {

            findAddressFromLocation(map!!.cameraPosition.target)

            true
        }
    }


    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun findAddressFromLocation(selectedLatLng: LatLng) {

        lifecycleScope.launch {

            val addresses = selectedLatLng.getAddressAsync(this@MapActivity)
                .await()

            addresses?.let { nsAddresses ->
                showAddressDialog(nsAddresses, selectedLatLng)
            }
        }
    }


    private fun showAddressDialog(addresses: List<Address>, latLng: LatLng) {


        when {

            addresses.isEmpty() -> Toast.makeText(
                this,
                getString(R.string.no_address_found),
                Toast.LENGTH_SHORT
            ).show()


            addresses.size == 1 -> sendResultToBackActivity(
                addresses[0].toCityName(),
                latLng
            )


            else -> AlertDialog.Builder(this)
                .setTitle("Select Address")
                .setItems(addresses.map { it.toCityName() }
                    .toTypedArray()) { _, i ->
                    sendResultToBackActivity(addresses[i].toCityName(), latLng)
                }.show()
        }
    }


    private fun sendResultToBackActivity(cityName: String, latLng: LatLng) {

        Intent().apply {

            this.putExtra(Constant.CityType.CITY_NAME, cityName)
            this.putExtra(Constant.CityType.LATITUDE, latLng.latitude)
            this.putExtra(Constant.CityType.LONGITUDE, latLng.longitude)

            setResult(RESULT_OK, this)

            finish()
        }
    }

    private fun Address.toCityName(): String {

        var city = this.locality

        if (city.isEmpty())
            city = this.adminArea

        return city
    }

    companion object {
        private val TAG = MapActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        private const val M_MAX_ENTRIES = 5
    }


}
