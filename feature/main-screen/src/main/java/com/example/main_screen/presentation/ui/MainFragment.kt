package com.example.main_screen.presentation.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.main_screen.R
import com.example.main_screen.databinding.MainFragmentBinding
import com.example.main_screen.domain.entity.Offer
import com.example.main_screen.presentation.MainState
import com.example.main_screen.presentation.MainViewModel
import com.example.main_screen.presentation.TicketsContent
import com.example.main_screen.presentation.ui.bottom_sheet.BottomSheetFragment
import com.example.utils.Constants
import com.example.utils.FileUtil
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Locale

class MainFragment : Fragment() {

    companion object {

        private const val MAIN_SCREEN_CACHE = "mainScreenCache.txt"

        fun getNewInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val mainViewModel: MainViewModel by viewModel()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: MainAdapter? = null

    private var departureCity = Constants.EMPTY_STRING
    private var destinationCity = Constants.EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openBottomSheetClickListener(binding.contentInitial.citySelectionView.edBottom)

        mainViewModel.errorChannel.onEach {
            Toast.makeText(context, "Поиск не удался", Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        loadData()
    }

    private fun loadData() {
        mainViewModel.loadData()

        mainViewModel.state.onEach {
            when (it) {
                is MainState.Loading -> binding.renderLoading()
                is MainState.Content -> binding.renderOffersContent(it)
                is MainState.InitialLoadError -> Toast.makeText(
                    context,
                    R.string.server_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupRecyclerView(offerList: List<Offer>) {
        adapter = MainAdapter(offerList)
        binding.contentInitial.rvTickets.adapter = adapter
    }

    private fun MainFragmentBinding.renderLoading() {
        progressBar.isVisible = true
        clContent.isVisible = false
    }

    private fun MainFragmentBinding.hideProgress() {
        progressBar.isVisible = false
        clContent.isVisible = true
    }

    private fun MainFragmentBinding.renderOffersContent(state: MainState.Content) {

        hideProgress()

        if (state.ticketsContent != null) {
            renderTicketsContent(state.ticketsContent)
        } else {
            setupOffersContent()
            setupRecyclerView(state.offerList)
        }
    }

    private fun MainFragmentBinding.renderTicketsContent(ticketsContent: TicketsContent) {
        hideProgress()
        setupTicketsContent(ticketsContent)
    }

    private fun MainFragmentBinding.setupOffersContent() {

        departureCity = FileUtil.readFromCache(requireContext(), MAIN_SCREEN_CACHE)

        contentSelectedCountry.root.isVisible = false
        contentInitial.root.isVisible = true

        contentInitial.citySelectionView.edBottom.keyListener = null
        contentInitial.citySelectionView.edBottom.isFocusable = false

        contentInitial.citySelectionView.edTop.setText(departureCity)
        contentInitial.citySelectionView.edTop.doOnTextChanged { text, _, _, _ ->
            departureCity = text.toString()
        }
    }


    private fun MainFragmentBinding.setupTicketsContent(ticketsContent: TicketsContent) {
        contentInitial.root.isVisible = false

        renderCitySelection(ticketsContent)
        openBottomSheetClickListener(binding.contentSelectedCountry.citySelectionView.edBottom)

        contentSelectedCountry.apply {
            root.isVisible = true

            btnShowTickets.setOnClickListener {
                val ticketDirection = "$departureCity — $destinationCity"
                val ticketDate = btnDate.text.toString()
                mainViewModel.navigateToTicketsScreen(ticketDirection, ticketDate)
            }

            btnDate.setOnClickListener {
                setupCalendar()
            }

            ticketsContent.ticketList.forEachIndexed { index, ticketOffer ->
                val (view, circle) = when (index) {
                    0 -> firstDestination to R.drawable.circle_red
                    1 -> secondDestination to R.drawable.circle_blue
                    2 -> thirdDestination to R.drawable.circle_white
                    else -> return
                }
                view.tvAviaLine.text = ticketOffer.title
                view.ivCircle.setImageResource(circle)
                view.tvTime.text = ticketOffer.timeRange.toString()
                view.tvPrice.text = getString(R.string.price_placeholder_two, ticketOffer.price.value.toString())
            }
        }

    }

    private fun MainFragmentBinding.renderCitySelection(ticketsContent: TicketsContent) {
        contentSelectedCountry.citySelectionView.apply {
            edTop.setText(departureCity)
            edBottom.isFocusable = false

            edTop.doOnTextChanged { text, _, _, _ ->
                departureCity = text.toString()
            }

            destinationCity = ticketsContent.destinationCity
            edBottom.setText(ticketsContent.destinationCity)

            searchIcon.setImageResource(R.drawable.ic_left_arrow)
            clearIcon.isVisible = true
            changeIcon.isVisible = true

            clearIcon.setOnClickListener{
                edBottom.setText(Constants.EMPTY_STRING)
                destinationCity = Constants.EMPTY_STRING
            }

            changeIcon.setOnClickListener {
                departureCity = edBottom.text.toString()
                destinationCity = edTop.text.toString()
                edTop.setText(departureCity)
                edBottom.setText(destinationCity)
            }
        }
    }

    private fun setupCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val listener = OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale("ru"))
            val monthStr = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ru"))
            binding.contentSelectedCountry.btnDate.text = getString(R.string.calendar_date, dayOfMonth, monthStr.dropLast(1), dayOfWeek)
        }
        DatePickerDialog(requireContext(), listener, year, month, day).show()
    }

    private fun openBottomSheetClickListener(view: View) {
        view.setOnClickListener {
            if (childFragmentManager.findFragmentByTag("bottom_sheet") == null) {
                BottomSheetFragment.getNewInstance(departureCity, destinationCity).show(childFragmentManager, "bottom_sheet")
            }
        }
    }

    override fun onPause() {
        cacheData()
        super.onPause()
    }

    private fun cacheData() {
        FileUtil.createAndWriteToCache(
            requireContext(),
            MAIN_SCREEN_CACHE,
            departureCity
        ) {}
    }

    override fun onDestroy() {
        adapter = null
        binding.contentInitial.rvTickets.adapter = null
        super.onDestroy()
    }
}