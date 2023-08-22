package com.aeon.exchangeratesapp.ui.currency

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.utils.DelegateUtils.lazyUnsafe

class CurrencyViewHolder(view: View, private val onCurrencyCodeClicked: (code: String) -> Unit) :
    ViewHolder(view) {

    private val tvCurrencyCode: TextView by lazyUnsafe { itemView.findViewById(R.id.tvCurrencyCode) }

    fun bind(currencyCode: String) {
        tvCurrencyCode.apply {
            text = currencyCode
            setOnClickListener { onCurrencyCodeClicked(currencyCode) }
        }
    }
}
