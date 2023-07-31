package com.example.water_tank_monitor


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.location.LocationManager
import android.media.ExifInterface
import android.media.Image
import android.media.ImageReader
import android.media.MediaScannerConnection
import android.net.Uri
//import androidx.appcompat.app.AppCompatActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.Data
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.support.v4.content.FileProvider
import android.telephony.SmsManager

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import java.text.SimpleDateFormat
import java.util.*

import com.example.water_tank_monitor.MainActivity
import java.io.*
import java.sql.Time
import java.sql.Timestamp

class Trip_start : AppCompatActivity() {
    lateinit var cam_1 : Button
    lateinit var cam_2 : Button
    lateinit var cam_3 : Button
    lateinit var img_1 : ImageView
    lateinit var img_2 : ImageView
    lateinit var img_3 : ImageView
    lateinit var user: TextView
    lateinit var time_1 : TextView
    lateinit var time_2 : TextView
    lateinit var time_3 : TextView
    lateinit var loc_man : LocationManager
    lateinit var loc : TextView
    lateinit var exit : Button
    internal var op : File? = null
    var dbq : Database_Image = Database_Image(this)


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_start)
        cam_1 = findViewById(R.id.cam_1)
        cam_2 = findViewById(R.id.cam_2)
        cam_3 = findViewById(R.id.cam_3)
        img_1 = findViewById(R.id.img_view_1)
        img_2 = findViewById(R.id.img_view_2)
        img_3 = findViewById(R.id.img_view_3)

        time_1 = findViewById(R.id.img_1_time)
        time_2 = findViewById(R.id.img_2_time)
        time_3 = findViewById(R.id.img_3_time)


        exit = findViewById(R.id.exit)
        loc = findViewById(R.id.loc)


        val bundle=Bundle()

        exit.isEnabled=false

        cam_1.setOnClickListener {


            val intent1 = Intent(this,Database_Image::class.java)
            intent1.putExtras(bundle)

            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)


            try{

                val smsManager : SmsManager = SmsManager.getDefault()
                val smsBody = StringBuffer()
                smsBody.append(Uri.parse(time_1.text.toString()))

                smsManager.sendTextMessage("7829012588",null, smsBody.toString(),null,null)

                Toast.makeText(applicationContext,"Location sent", Toast.LENGTH_SHORT).show()


            }
            catch (e : Exception){
                Toast.makeText(applicationContext,"Location is not correct", Toast.LENGTH_SHORT).show()
            }
            startActivityForResult(Intent.createChooser(intent,"Select file"), pic_id_1)

        }



        cam_2.setOnClickListener {
            time_2.text= Calendar.getInstance().time.toString()
            val intent1 = Intent(this,Database_Image::class.java)
            intent1.putExtras(bundle)

            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            //val date=Date()
            op = File(dir, "${Calendar.getInstance().time.toString()}.jpeg")
            try{

                val smsManager : SmsManager = SmsManager.getDefault()
                val smsBody = StringBuffer()
                smsBody.append(Uri.parse(time_2.text.toString()))

                smsManager.sendTextMessage("7829012588",null, smsBody.toString(),null,null)

                Toast.makeText(applicationContext,"Location sent", Toast.LENGTH_SHORT).show()


            }
            catch (e : Exception){
                Toast.makeText(applicationContext,"Location is not correct", Toast.LENGTH_SHORT).show()
            }
            startActivityForResult(Intent.createChooser(intent,"Select file"), pic_id_2)




        }

        cam_3.setOnClickListener {
            time_3.text= Calendar.getInstance().time.toString()
            val intent1 = Intent(this,Database_Image::class.java)
            intent1.putExtras(bundle)

            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            //val date=Date()
            op = File(dir, "${Calendar.getInstance().time.toString()}.jpeg")
            try{

                val smsManager : SmsManager = SmsManager.getDefault()
                val smsBody = StringBuffer()
                smsBody.append(Uri.parse(time_3.text.toString()))

                smsManager.sendTextMessage("7829012588",null, smsBody.toString(),null,null)


                Toast.makeText(applicationContext,"Location sent", Toast.LENGTH_SHORT).show()


            }
            catch (e : Exception){
                Toast.makeText(applicationContext,"Location is not correct", Toast.LENGTH_SHORT).show()
            }
            startActivityForResult(Intent.createChooser(intent,"Select file"), pic_id_3)



        }
        if(img_1.drawable!=null && img_2.drawable!=null && img_3.drawable!=null) {

            exit.isEnabled=true

            exit.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Match the request 'pic id with requestCode

        if(resultCode== RESULT_OK){
            if (requestCode == pic_id_1) {
                val uri= data?.data
                val filepath1 = getFilePathFromUri(context = this ,uri)
                time_1.text=filepath1
                onselectgallery_1(data)

            }
            if(requestCode== pic_id_2) {
                val uri = data?.data
                val filepath2 = getFilePathFromUri(context = this ,uri)
                time_2.text=filepath2
                onselectgallery_2(data)
            }
            if (requestCode == pic_id_3) {
                if (data != null) {
                    val uri = data?.data
                    val filepath3 = getFilePathFromUri(context = this ,uri)
                    time_3.text=filepath3

                    onselectgallery_3(data)
                }



            }
        }



    }

    private fun getFilePathFromUri(context: Context?, uri: Uri?): String? {
        var filePath : String ?= null
        if(DocumentsContract.isDocumentUri(context,uri)){
            if (isMediaDocument(uri)) {
                val documentId = DocumentsContract.getDocumentId(uri)
                val split = documentId.split(":").toTypedArray()
                val type = split[0]
                val contentUri: Uri = when (type) {
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> MediaStore.Files.getContentUri("external")
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                filePath = getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }
        return  filePath

    }

    private fun getDataColumn(context: Context?, uri: Uri?, selection: String, selectionArgs: Array<String>): String? {
        val column = "_data"
        val projection = arrayOf(column)
        var filePath: String? = null
        if (uri != null) {
            if (context != null) {
                context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(column)
                        filePath = cursor.getString(columnIndex)
                    }
                }
            }
        }
        var finalfp = filePath?.substring(32,48)
        return finalfp

    }

    private fun isMediaDocument(uri: Uri?): Boolean {

            return "com.android.providers.media.documents" == uri?.authority

    }

    private fun onselectgallery_1(data: Intent?) {
        var bm : Bitmap? = null
        if(data!=null){
            try{
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver,data.data)


            }
            catch (e : IOException){
                e.printStackTrace()
            }

        }
        img_1.setImageBitmap(bm)


    }
    private fun onselectgallery_2(data: Intent?) {
        var bm : Bitmap? = null
        if(data!=null){
            try{
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver,data.data)

            }
            catch (e : IOException){
                e.printStackTrace()
            }

        }
        img_2.setImageBitmap(bm)

    }
    private fun onselectgallery_3(data: Intent?) {
        var bm : Bitmap? = null
        if(data!=null){
            try{
                bm = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver,data.data)

            }
            catch (e : IOException){
                e.printStackTrace()
            }

        }
        img_3.setImageBitmap(bm)

    }


    companion object{
        const val pic_id_1=123
        const val pic_id_2=124
        const val pic_id_3=125
    }
}


