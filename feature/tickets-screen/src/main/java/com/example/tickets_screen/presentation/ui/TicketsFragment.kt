package com.example.tickets_screen.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tickets_screen.R
import com.example.tickets_screen.databinding.TicketsFragmentBinding
import com.example.tickets_screen.presentation.TicketsState
import com.example.tickets_screen.presentation.TicketsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsFragment: Fragment() {

    companion object {

        private const val TICKET_DIRECTION = "ticket_direction"
        private const val TICKET_DATE = "ticket_date"

        fun getNewInstance(ticketDirection: String, ticketDate: String): TicketsFragment {
            return TicketsFragment().apply {
                arguments = Bundle().apply {
                    putString("ticket_direction", ticketDirection)
                    putString("ticket_date", ticketDate)
                }
            }
        }
    }

    private val ticketsViewModel: TicketsViewModel by viewModel()

    private var _binding: TicketsFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: TicketsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TicketsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        requireActivity().onBackPressedDispatcher.addCallback {
            ticketsViewModel.navigateBack()
        }
        binding.ivArrow.setOnClickListener {
            ticketsViewModel.navigateBack()
        }
        loadData()
    }

    private fun loadData() {
        ticketsViewModel.loadData()
        ticketsViewModel.state.onEach {
            when (it) {
                is TicketsState.Loading -> binding.renderLoading()
                is TicketsState.Content -> binding.renderContent(it)
                is TicketsState.Error -> Toast.makeText(
                    context,
                    R.string.server_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun TicketsFragmentBinding.renderLoading() {
        progressBar.isVisible = true
        clContent.isVisible = false
        rvTickets.isVisible = false
    }

    private fun TicketsFragmentBinding.renderContent(state: TicketsState.Content) {
        hideProgress()
        adapter = TicketsAdapter(state.ticketList)
        rvTickets.adapter = adapter

        tvFlightDirection.text = requireArguments().getString(TICKET_DIRECTION)
        tvTime.text = requireArguments().getString(TICKET_DATE)
    }


    private fun TicketsFragmentBinding.hideProgress() {
        progressBar.isVisible = false
        clContent.isVisible = true
        rvTickets.isVisible = true
        doubleBtn.root.isVisible = true
    }

    override fun onDestroy() {
        adapter = null
        binding.rvTickets.adapter = null
        super.onDestroy()
    }
}