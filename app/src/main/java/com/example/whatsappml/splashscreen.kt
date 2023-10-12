package com.example.whatsappml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val handler = Handler(Looper.getMainLooper())
        var ivlog:ImageView = findViewById(R.id.iv_whatsapplogo)
        var ivanim:Animation = AnimationUtils.loadAnimation(this,R.anim.scale)
        ivlog.startAnimation(ivanim)
        handler.postDelayed({
            var i_home:Intent = Intent(this,MainActivity::class.java)
            startActivity(i_home)
            finish()
        },2000)
    }
}

