package com.example.main_screen.presentation.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.main_screen.R
import com.example.main_screen.databinding.BottomSheetFragmentBinding
import com.example.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    companion object {

        private const val DEPARTURE_CITY = "departure_city"
        private const val DESTINATION_CITY = "destination_city"

        fun getNewInstance(departureCity: String, destinationCity: String): BottomSheetFragment {
            return BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString("departure_city", departureCity)
                    putString("destination_city", destinationCity)
                }
            }
        }
    }

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetViewModel: BottomSheetViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        expandFullScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setupViews()
    }

    private fun BottomSheetFragmentBinding.setupViews() {
        setupCitySelection()
        setupIcons()
        setupTowns()
        setDestinationCityListener()
    }

    private fun BottomSheetFragmentBinding.setDestinationCityListener() {
        citySelectionView.edBottom.doOnTextChanged { text, _, _, _ ->
            bottomSheetViewModel.handleDestinationCity(text?.toString())
        }
    }

    private fun BottomSheetFragmentBinding.setupCitySelection() {
        citySelectionView.apply {
            changeIcon.isVisible = false
            clearIcon.isVisible = true
            searchIcon.isVisible = false

            edTop.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_air_plane, 0, 0, 0)
            edTop.compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.dimen_8dp)
            edTop.isFocusable = false
            edTop.setText(requireArguments().getString(DEPARTURE_CITY))

            edBottom.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
            edBottom.compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.dimen_8dp)
            edBottom.setText(requireArguments().getString(DESTINATION_CITY))

            clearIcon.isClickable = true
            clearIcon.setOnClickListener {
                edBottom.setText(Constants.EMPTY_STRING)
            }

            ball.root.setOnClickListener {
                edBottom.setText(R.string.icon_ball_description)
            }
        }
    }

    private fun BottomSheetFragmentBinding.setupIcons() {
        route.ivIcon.setImageResource(R.drawable.ic_route)
        route.tvIcon.text = getString(R.string.icon_route_description)
        route.cvIcon.setCardBackgroundColor(getColor(requireContext(), R.color.green_1))
        route.root.setOnClickListener { bottomSheetViewModel.navigateToStub() }

        ball.ivIcon.setImageResource(R.drawable.ic_ball)
        ball.tvIcon.text = getString(R.string.icon_ball_description)
        ball.cvIcon.setCardBackgroundColor(getColor(requireContext(), R.color.blue_1))

        calendar.ivIcon.setImageResource(R.drawable.ic_calendar)
        calendar.tvIcon.text = getString(R.string.icon_calendar_description)
        calendar.cvIcon.setCardBackgroundColor(getColor(requireContext(), R.color.dark_blue))
        calendar.root.setOnClickListener { bottomSheetViewModel.navigateToStub() }

        fire.ivIcon.setImageResource(R.drawable.ic_fire)
        fire.tvIcon.text = getString(R.string.icon_fire_description)
        fire.cvIcon.setCardBackgroundColor(getColor(requireContext(), R.color.red_1))
        fire.root.setOnClickListener { bottomSheetViewModel.navigateToStub() }
    }

    private fun BottomSheetFragmentBinding.setupTowns() {
        cityIstanbul.ivCity.setImageResource(R.drawable.istanbul)
        cityIstanbul.tvCity.text = getString(R.string.city_istanbul)
        cityIstanbul.root.setOnClickListener { citySelectionView.edBottom.setText(cityIstanbul.tvCity.text) }

        citySochi.ivCity.setImageResource(R.drawable.sochi)
        citySochi.tvCity.text = getString(R.string.city_sochi)
        citySochi.root.setOnClickListener { citySelectionView.edBottom.setText(citySochi.tvCity.text) }

        cityPhuket.ivCity.setImageResource(R.drawable.phucket)
        cityPhuket.tvCity.text = getString(R.string.city_phuket)
        cityPhuket.root.setOnClickListener { citySelectionView.edBottom.setText(cityPhuket.tvCity.text) }
    }

    private fun BottomSheetFragment.expandFullScreen() {
        dialog?.let {
            val bottomSheet = it as BottomSheetDialog
            val layout = bottomSheet.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
            if (layout != null) {
                layout.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                view?.let { vw ->

                    val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(layout)
                    behavior.isHideable = true
                    behavior.skipCollapsed = true
                    behavior.peekHeight = vw.measuredHeight
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}