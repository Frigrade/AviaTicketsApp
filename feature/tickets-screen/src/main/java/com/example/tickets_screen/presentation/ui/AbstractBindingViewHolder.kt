package com.example.tickets_screen.presentation.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractBindingViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun onBind(itemPosition: Int)
}