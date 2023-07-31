package com.example.water_tank_monitor
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var user : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var signup : Button
    lateinit var pwd : TextView

    // @SuppressLint("MissingInflatedId", "Range")
    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        user=findViewById(R.id.username)
        login=findViewById(R.id.login)
        password=findViewById(R.id.password)
        signup=findViewById(R.id.forgot)
        pwd = findViewById(R.id.pwd)




        signup.setOnClickListener {
            val db = DatabaseHelper(this,null)

            val name = user.text.toString()
            val passwd =password.text.toString()
            db.adduser(name,passwd)


        }
        login.setOnClickListener {
            val db = DatabaseHelper(this,null)
            val cursor=db.getuser(user.text.toString())
            cursor!!.moveToFirst()
            val bundle = Bundle()

            if(password.text.toString()==cursor.getString(cursor.getColumnIndex(DatabaseHelper.pwd_col))) {
                val intent = Intent(this,Location_Sms::class.java)
                intent.putExtras(bundle)



                pwd.append(cursor.getString(cursor.getColumnIndex(DatabaseHelper.pwd_col)) + "\n")


                while (cursor.moveToNext()) {
                 pwd.append(cursor.getString(cursor.getColumnIndex(DatabaseHelper.pwd_col)) + "\n")

                }
                startActivity(intent)

                cursor.close()
            }
            else
            {
                Toast.makeText(this,"WRONG PASSWORD",Toast.LENGTH_SHORT).show()
                login.isEnabled=false
            }

        }


    }


}