package com.aeon.exchangeratesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.ui.viewholder.ExchangeRateViewHolder

class ExchangeRateAdapter(private val data: MutableList<ExchangeRateDto>) :
    RecyclerView.Adapter<ExchangeRateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_exchange_rate, parent, false)
        return ExchangeRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun setData(newData: List<ExchangeRateDto>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }
}