package com.example.main_screen.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main_screen.R
import com.example.main_screen.databinding.MainItemBinding
import com.example.main_screen.domain.entity.Offer
import com.example.utils.Helpers

class MainAdapter(
    private val ticketsList: List<Offer>
) : RecyclerView.Adapter<AbstractBindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractBindingViewHolder {
        return TicketViewHolder(
            MainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TicketViewHolder(private val itemBinding: MainItemBinding) : AbstractBindingViewHolder(itemBinding.root) {

        override fun onBind(itemPosition: Int) = itemBinding.run {
            val content = ticketsList[itemPosition]

            tvImageDescription.text = content.title
            tvCity.text = content.town
            tvPrice.text = itemBinding.root.context.getString(
                R.string.price_placeholder,
                Helpers.formatPrice(content.price.value)
            )

            when (content.id) {
                1 -> ivTicket.setImageResource(R.drawable.first)
                2 -> ivTicket.setImageResource(R.drawable.second)
                3 -> ivTicket.setImageResource(R.drawable.third)
            }
            return@run
        }
    }

    override fun getItemCount() = ticketsList.size

    override fun onBindViewHolder(holder: AbstractBindingViewHolder, position: Int) =
        holder.onBind(position)
}