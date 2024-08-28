package com.example.shopclothes.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import kotlin.jvm.Throws

class GPSHandler(
    private val fragment: Fragment,
    private val successCallback: (Location)->Unit
) {

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    fun onCreate(){
        createCheckPermission()

        createFusedClient()
        createCallback()
    }

    fun onResume(){
        checkPermission()
    }

    fun onPause() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun createCallback(){
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.locations.first()
                successCallback(location)
            }
        }
    }

    private fun createFusedClient(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.requireContext())
    }

    @Throws(SecurityException::class)
    private fun createRequest(){
        locationRequest = LocationRequest.Builder(10000).apply {

        }.build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(fragment.requireContext())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                try {
                    exception.startResolutionForResult(fragment.requireActivity(), 0)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun createCheckPermission(){
        locationPermissionRequest = fragment.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    createRequest()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    createRequest()
                } else -> {
                    // No location access granted.
                }
            }
        }
    }

    private fun checkPermission(){
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}