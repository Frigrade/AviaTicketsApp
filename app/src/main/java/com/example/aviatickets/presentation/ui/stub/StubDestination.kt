package com.example.aviatickets.presentation.ui.stub

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class StubDestination: FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment {
        return StubFragment.getNewInstance()
    }
}