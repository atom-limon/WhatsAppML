package com.example.whatsappml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.whatsappml.databinding.ActivityMainBinding
import com.example.whatsappml.databinding.ActivitySignInBinding
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding   //view binder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //circling to sign up and sign in
        binding.tvCreateaccount.setOnClickListener{
            val intent:Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }



    }
}