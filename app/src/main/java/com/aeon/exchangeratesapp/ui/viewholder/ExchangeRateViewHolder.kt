package com.aeon.exchangeratesapp.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.domain.ExchangeRateDto

class ExchangeRateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvCurrencyCode: TextView by lazy { itemView.findViewById(R.id.tvCurrencyCode) }
    private val tvExchangeRateValue: TextView by lazy { itemView.findViewById(R.id.tvExchangeRateValue) }
    private val ivFavourite: ImageView by lazy { itemView.findViewById(R.id.ivFavourite) }

    fun bind(exchangeRateDto: ExchangeRateDto) {
        exchangeRateDto.apply {
            tvCurrencyCode.text = currencyCode
            tvExchangeRateValue.text = exchangeRateValue
            ivFavourite.setImageResource(
                if (isFavourite) R.drawable.ic_star
                else R.drawable.ic_star_empty
            )
        }
    }
}