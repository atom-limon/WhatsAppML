package com.example.whatsappml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.trimmedLength
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import kotlin.math.sign
import kotlin.time.Duration

class MainActivity : AppCompatActivity() {
    private lateinit var username:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var signup:Button

    private lateinit var dbref:DatabaseReference
    private lateinit var dbauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvalready:TextView = findViewById(R.id.tv_already)
        tvalready.setOnClickListener{
            var intent:Intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        username = findViewById(R.id.et_username)
        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        signup = findViewById(R.id.btn_signup)

        dbref = FirebaseDatabase.getInstance().getReference()
        dbauth = FirebaseAuth.getInstance()

        signup.setOnClickListener{
            var txtusername:String = username.text.toString()
            var txtemail:String = email.text.toString()
            var txtpassword:String = password.text.toString()

            if(TextUtils.isEmpty(txtusername)||TextUtils.isEmpty(txtemail)
                || TextUtils.isEmpty(txtpassword))
                Toast.makeText(this,"Empty Credentials",Toast.LENGTH_SHORT).show()
            else if(txtpassword.trimmedLength()<8)
                Toast.makeText(this,"password is too short",Toast.LENGTH_SHORT).show()
            else
                registerUser(txtusername,txtemail,txtpassword)

        }

    }
    private fun registerUser(username:String, email:String, password:String){
        dbauth.createUserWithEmailAndPassword(email,password).addOnSuccessListener{
            var map = HashMap<String,String>()
            map.put("name",username)
            map.put("email",email)
            map.put("id", dbauth.currentUser!!.uid)
            dbref.child("User").child(dbauth.currentUser!!.uid).setValue(map).addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this,"user created Successfully",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


