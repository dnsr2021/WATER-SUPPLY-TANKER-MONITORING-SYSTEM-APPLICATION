package com.example.water_tank_monitor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class Database_Image (context : Context) : SQLiteOpenHelper(context,db_name, null,db_version){


        var n : Int=0


        override fun onCreate(p0: SQLiteDatabase?) {
            while(n<2) {


                val q = "create table $table_name$n($img_col blob)"
                if (p0 != null) {
                    p0.execSQL(q)
                }
                n=n+1
            }


        }


        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            n=0
            while (n<2) {
                val q = "drop table if exists $table_name"
                if (p0 != null) {
                    p0.execSQL(q)
                }
            }
            onCreate(p0)
        }
        fun insert_img(img : String) : Boolean{
            val db = writableDatabase
            val cv = ContentValues()
            cv.put(img_col,img)
            val res=db.insert(table_name,null,cv)
            return if(res.equals(-1))
                false
            else
                true
        }


        companion object{

            private val db_name="project_mad"
            private val db_version=1
            val table_name= "image_tab"
            val img_col = "img_col"

        }
    }
