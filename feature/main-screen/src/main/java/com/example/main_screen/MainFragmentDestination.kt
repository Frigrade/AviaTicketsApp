package com.example.main_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.main_screen.presentation.ui.MainFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class MainFragmentDestination : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment {
        return MainFragment.getNewInstance()
    }
}