package com.example.aviatickets.presentation.ui.stub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.aviatickets.R
import com.example.aviatickets.databinding.StubFragmentBinding
import com.example.aviatickets.presentation.StubRouterImpl
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class StubFragment: Fragment() {

    companion object {

        fun getNewInstance(): Fragment =
            StubFragment()
    }

    private val router: StubRouterImpl by inject()

    private lateinit var binding: StubFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StubFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).isVisible = false

        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).isVisible = true
            router.navigateBack()
        }

        binding.button.setOnClickListener {
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).isVisible = true
            router.navigateBack()
        }
    }
}