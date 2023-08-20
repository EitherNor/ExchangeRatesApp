package com.aeon.exchangeratesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.ui.viewholder.CurrencyViewHolder

class CurrencyAdapter(
    private val data: MutableList<String>,
    private val onCurrencyCodeClicked: (code: String) -> Unit,
) : Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(view, onCurrencyCodeClicked)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun setData(newData: Collection<String>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }
}