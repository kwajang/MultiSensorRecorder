package ksci.com.multisensorrecorder

import android.annotation.SuppressLint
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast

class LocationController(_locationManager:LocationManager): LocationListener {

    private val locationManager = _locationManager

    @SuppressLint("MissingPermission")
    fun registerLocation() {
        if (MainPageActivity.locationPermission) {
            val criteria = Criteria()
            val provider = locationManager.getBestProvider(criteria, false)
            val location = locationManager.getLastKnownLocation(provider)

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
            if (location != null) {
                locationDataRecord(MainPageActivity.locationData, location.latitude, location.longitude)
            } else {
                Toast.makeText(MainPageActivity(), "location not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun locationDataRecord(locationData: FloatArray, latitude: Double, longitude: Double) {
        val latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS)
        val latitudeSplit = latitudeDegrees.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        locationData[0] = ("%.5f".format(latitudeSplit[0].toDouble() +
                latitudeSplit[1].toDouble() / 60 +
                latitudeSplit[2].toDouble() / 3600)).toFloat()
        if (latitude < 0) locationData[0] = -locationData[0]

        val longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS)
        val longitudeSplit = longitudeDegrees.split((":").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        locationData[1] = ("%.5f".format(longitudeSplit[0].toDouble() +
                longitudeSplit[1].toDouble() / 60 +
                longitudeSplit[2].toDouble() / 3600)).toFloat()
        if (longitude < 0) locationData[1] = -locationData[1]
    }

    override fun onLocationChanged(location: Location?) {
        if (MainPageActivity.locationPermission && MainPageActivity.mRecord) {
            registerLocation()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}