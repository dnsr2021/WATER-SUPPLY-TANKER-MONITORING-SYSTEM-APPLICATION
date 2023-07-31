package com.example.water_tank_monitor

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context, factory : SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,db_name,factory,db_version) {

        override fun onCreate(p0: SQLiteDatabase?) {
            val q=("create table $table_name ($user_name_col text primary key,$pwd_col text)")
            if (p0 != null) {
                p0.execSQL(q)
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            if (p0 != null) {
                p0.execSQL("drop table if exists $table_name")
            }
            onCreate(p0)
        }

        fun adduser(user : String,pwd : String){


            val p0 = this.writableDatabase
            val q="insert into $table_name values('$user','$pwd')"
            p0.execSQL(q)
            p0.close()
        }


        fun getuser(user : String): Cursor?{
            val p0 = this.readableDatabase
            return p0.rawQuery("select pwd_col from $table_name where user_name_col='$user'",null)

        }
        companion object{
            private val db_name="project_mad"
            private val db_version=1
            val table_name = "USER_PWD"
            val user_name_col = "user_name_col"
            val pwd_col = "pwd_col"


        }

    }
