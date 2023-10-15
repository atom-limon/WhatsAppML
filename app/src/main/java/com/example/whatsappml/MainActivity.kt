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
import com.example.whatsappml.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import kotlin.math.sign
import kotlin.time.Duration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding   //view binder
    private lateinit var db:FirebaseDatabase
    private lateinit var dbref:DatabaseReference
    private lateinit var dbauth:FirebaseAuth

    private fun nullcheck(name:String, email:String, pass:String): Boolean {
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)) return true
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sign up to sign in change activity
        binding.tvAlready.setOnClickListener{
            var intent:Intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        db = FirebaseDatabase.getInstance()
        dbref = db.getReference().child("userinfo")
        dbauth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener{
            var usrname:String = binding.etUsername.text.toString()
            var usremail:String = binding.etEmail.text.toString()
            var usrpass:String = binding.etEmail.text.toString()
            //check for empty credentials
            if(nullcheck(usrname,usremail,usrpass)){
                Toast.makeText(applicationContext, "Empty field's are not allowed", Toast.LENGTH_SHORT).show()
            }
            else{
                //check for username validity
                dbref.orderByChild("username").equalTo(usrname).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //if username alreday taken then
                        if(snapshot.exists()){
                            Toast.makeText(applicationContext, "username taken", Toast.LENGTH_SHORT).show()
                        }
                        // if not taken
                        else{
                            dbauth.createUserWithEmailAndPassword(usremail,usrpass).addOnCompleteListener{task->
                                if(task.isSuccessful){
                                    //if user created successfully
                                    dbref.child(dbauth.currentUser!!.uid).child("username").setValue(usrname)
                                    Toast.makeText(applicationContext, "Sign Up Successfull", Toast.LENGTH_SHORT).show()
                                    //now pass the user to the sign in activity to log in
                                    var intent:Intent = Intent(applicationContext,SignInActivity::class.java)
                                    startActivity(intent)
                                    finish()    //finish signup activity after successfull account creation
                                }
                                else{
                                    //if fails
                                    Toast.makeText(applicationContext, "Email Exists \n Sign In", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) { TODO("Not yet implemented") }
                })
            }
        }



    }
}


