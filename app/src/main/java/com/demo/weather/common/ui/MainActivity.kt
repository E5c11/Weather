package com.demo.weather.common.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.demo.weather.R
import com.demo.weather.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navHostFragment.navController.apply {
            binding.bottomNav.selectedItemId = R.id.home
            binding.bottomNav.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.history -> {
                        navigate(R.id.historyFragment)
                        return@setOnItemSelectedListener true
                    }
                    R.id.home -> {
                        navigate(R.id.weatherFragment)
                        return@setOnItemSelectedListener true
                    }
                    R.id.map -> {
                        navigate(R.id.mapFragment)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }
    }
}