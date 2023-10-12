package com.example.whatsappml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val tvcreate:TextView = findViewById(R.id.tv_createaccount)
        tvcreate.setOnClickListener{
            val intent:Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}