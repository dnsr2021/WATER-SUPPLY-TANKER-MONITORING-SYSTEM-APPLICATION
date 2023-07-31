package com.example.water_tank_monitor

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.widget.Toast

class GPSTracker (private val context: Context) : LocationListener{

    fun getLocation() : Location? {
        val lm=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGPSEnabled){
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10f,this)
            val l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            return l

        }
        else{
            Toast.makeText(context,"Enable GPS",Toast.LENGTH_SHORT).show()
        }
        return null

    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }

}