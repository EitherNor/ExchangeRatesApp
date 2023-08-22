package com.aeon.exchangeratesapp.ui.ratelist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.utils.DelegateUtils.lazyUnsafe

class ExchangeRateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvCurrencyCode: TextView by lazyUnsafe { itemView.findViewById(R.id.tvCurrencyCode) }
    private val tvExchangeRateValue: TextView by lazyUnsafe { itemView.findViewById(R.id.tvExchangeRateValue) }
    private val ivFavourite: ImageView by lazyUnsafe { itemView.findViewById(R.id.ivFavourite) }
    private val tapzoneFavourite: View by lazyUnsafe { itemView.findViewById(R.id.tapzoneFavourite) }

    fun bind(exchangeRateDto: ExchangeRateDto, onFavouriteClicked: (dto: ExchangeRateDto) -> Unit) {
        exchangeRateDto.apply {
            if (tvCurrencyCode.text != currencyCode) {
                tvCurrencyCode.text = currencyCode
            }
            tvExchangeRateValue.text = exchangeRateValue.toString()
            ivFavourite.setImageResource(
                if (isFavourite) R.drawable.ic_star
                else R.drawable.ic_star_empty
            )
            tapzoneFavourite.setOnClickListener { onFavouriteClicked(this) }
        }
    }
}
