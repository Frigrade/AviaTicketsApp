package com.example.aviatickets.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ActivityMainBinding
import com.example.aviatickets.presentation.ui.stub.StubDestination
import com.example.main_screen.MainFragmentDestination
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: Navigator = AppNavigator(this, R.id.nav_host_fragment_activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupBottomNavigation()

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(MainFragmentDestination())))
        }
    }

    private fun setupBottomNavigation() {
        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    applyCommand(Replace(MainFragmentDestination()))
                    true
                }

                else -> {
                    applyCommand(Forward(StubDestination()))
                    false
                }
            }
        }

    }

    private fun applyCommand(command: Command) {
        navigator.applyCommands(arrayOf(command))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}