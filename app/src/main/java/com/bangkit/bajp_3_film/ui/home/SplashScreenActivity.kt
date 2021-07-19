package com.bangkit.bajp_3_film.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkit.bajp_3_film.R

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, splashScreenTime)

    }
}