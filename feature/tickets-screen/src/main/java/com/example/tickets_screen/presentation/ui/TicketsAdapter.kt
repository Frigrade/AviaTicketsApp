package com.example.tickets_screen.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tickets_screen.R
import com.example.tickets_screen.databinding.TicketsItemBinding
import com.example.tickets_screen.domain.entity.Ticket
import com.example.utils.Helpers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class TicketsAdapter(
    private val ticketsList: List<Ticket>
) : RecyclerView.Adapter<AbstractBindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractBindingViewHolder {
        return TicketViewHolder(
            TicketsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TicketViewHolder(private val itemBinding: TicketsItemBinding) : AbstractBindingViewHolder(itemBinding.root) {

        override fun onBind(itemPosition: Int) = itemBinding.run {
            val content = ticketsList[itemPosition]

            if (content.badge?.isNotEmpty() == true) {
                tvBadge.isVisible = true
                tvBadge.text = content.badge
            }

            tvPrice.text = itemBinding.root.context.getString(
                R.string.price_placeholder,
                Helpers.formatPrice(content.price.value)
            )

            tvStartOfFlight.text = LocalDateTime.parse(content.departure.date).format(DateTimeFormatter.ofPattern("HH:mm"))
            tvEndOfFlight.text = LocalDateTime.parse(content.arrival.date).format(DateTimeFormatter.ofPattern("HH:mm"))
            tvFromAirPortCode.text = content.departure.airport
            tvToAirPortCode.text = content.arrival.airport

            val timeOfFlight = calculateTimeOfFlight(content.departure.date, content.arrival.date)
            tvTimeOfFlight.text = itemBinding.root.context.getString(
                R.string.flight_time,
                timeOfFlight
            )
        }
    }

    private fun calculateTimeOfFlight(startDateTimeString: String, endDateTimeString: String): String {
        val startDateTime = LocalDateTime.parse(startDateTimeString)
        val endDateTime = LocalDateTime.parse(endDateTimeString)

        val hours = ChronoUnit.HOURS.between(startDateTime, endDateTime)
        val minutes = (ChronoUnit.MINUTES.between(startDateTime, endDateTime) % 60).toString().drop(1)

        return ("${hours}.$minutes")
    }

    override fun getItemCount() = ticketsList.size

    override fun onBindViewHolder(holder: AbstractBindingViewHolder, position: Int) =
        holder.onBind(position)
}