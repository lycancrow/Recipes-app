package com.example.recepiesapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.recepiesapp.R
import com.example.recepiesapp.view.homeView.HomeView

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSpash= installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Thread.sleep(800)
        screenSpash.setKeepOnScreenCondition{true}
        val intent = Intent(this, HomeView::class.java)
        startActivity(intent)
        finish()
    }
}