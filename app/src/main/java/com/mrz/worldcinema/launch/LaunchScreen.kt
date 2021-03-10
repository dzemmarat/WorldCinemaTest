package com.mrz.worldcinema.launch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mrz.worldcinema.R
import com.mrz.worldcinema.SignIn.SignIn
import com.mrz.worldcinema.SignUp.SignUp

class LaunchScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Handler().postDelayed({
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            var isFirstStart = sharedPreference.getBoolean("isFirstStart", true)

            if (!isFirstStart) {
                val intent = Intent(this, SignIn::class.java)
                startActivity(intent)
                finish()
            } else {
                startActivity(Intent(this, SignUp::class.java))
                var editor = sharedPreference.edit()
                editor.putBoolean("isFirstStart", false)
                editor.commit()
                finish()
            }
        }, 3000)
    }
}