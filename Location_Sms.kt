package com.example.water_tank_monitor

import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Location_Sms : AppCompatActivity() {

        lateinit var phone_no : EditText

        lateinit var location_text : TextView
        lateinit var send : Button
        lateinit var location : Location
        // @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_location_sms)
            phone_no = findViewById(R.id.phone_no)
            location_text = findViewById(R.id.location_text)
            send = findViewById(R.id.send)

            send.setOnClickListener {
                var num = phone_no.text.toString()
                val g=GPSTracker(applicationContext)
                val l=g.getLocation()
                val lat= l?.latitude
                val lon = l?.longitude
                val message = "https://maps.google.com/maps?q=$lat,$lon"


                try{

                    val smsManager : SmsManager = SmsManager.getDefault()
                    val smsBody = StringBuffer()
                    smsBody.append(Uri.parse(message))

                    smsManager.sendTextMessage(num,null, smsBody.toString(),null,null)

                    Toast.makeText(applicationContext,"Location sent", Toast.LENGTH_SHORT).show()


                }
                catch (e : Exception){
                    Toast.makeText(applicationContext,"Location is not correct", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this,Trip_start::class.java)
                startActivity(intent)
            }

        }

    }




