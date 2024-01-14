package com.example.recepiesapp.view.homeView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.recepiesapp.R
import com.example.recepiesapp.databinding.ActivityHomeViewBinding
import com.example.recepiesapp.view.aboutApp.AboutApp
import com.example.recepiesapp.view.home.Home
import com.example.recepiesapp.view.worldFoods.WorldFoods

class HomeView : AppCompatActivity() {

    private lateinit var binding: ActivityHomeViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val homeFragment = Home()
        replaceFragment(homeFragment)

        binding.profileViewBottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Home -> {
                    replaceFragment(Home())
                    this.binding.profileViewBottomNav.isEnabled = false
                }
                R.id.internationalFoods -> {
                    replaceFragment(WorldFoods())
                }
                R.id.aboutTheApp -> {
                    replaceFragment(AboutApp())
                }
                else ->{

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMenu, fragment)
        fragmentTransaction.commit()
    }

}