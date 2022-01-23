package com.jaemin.hermes.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.jaemin.hermes.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate

class LocationUtil(private val context: Context,private val currentLocationCallback: CurrentLocationCallback) {
     private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
     private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.equals(null)) {
                return
            }
            for (location in locationResult.locations) {
                if (location != null) {
                    getCurrentLocation()
                    fusedLocationProviderClient.removeLocationUpdates(this)

                }
            }
        }
    }
     fun isLocationEnabled(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val locationManager: LocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.isLocationEnabled
        } else {
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.LOCATION_MODE
            ) != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    fun launchLocationSettings(activity: Activity) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivity(intent)
    }
    fun getCurrentLocation(){
        if (checkLocationPermission()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocationCallback.onGetCurrentLocationSuccess(location)
                } else {
                    requestCurrentLocation()
                }
            }
        }
    }

    private fun requestCurrentLocation() {
        if (checkLocationPermission()) {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun requestLocationPermission(activity: AppCompatActivity) {
        val locationPermissionRequest = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(activity)
                if (this::fusedLocationProviderClient.isInitialized) {
                    requestCurrentLocation()
                }
            } else {
                Toast.makeText(activity, activity.getString(R.string.please_allow_location_app_permission), Toast.LENGTH_SHORT).show()
                activity.onBackPressed()
            }

        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    fun requestLocationPermission(fragment: Fragment) {
        val locationPermissionRequest = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(fragment.requireActivity())
                if (this::fusedLocationProviderClient.isInitialized) {
                    requestCurrentLocation()
                }
            } else {
                Toast.makeText(fragment.requireActivity(), fragment.getString(R.string.please_allow_location_app_permission), Toast.LENGTH_SHORT).show()
                fragment.requireActivity().onBackPressed()
            }

        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    interface CurrentLocationCallback{
        fun onGetCurrentLocationSuccess(location: Location)


    }

}