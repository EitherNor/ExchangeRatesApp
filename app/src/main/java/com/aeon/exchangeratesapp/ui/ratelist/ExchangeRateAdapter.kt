package com.aeon.exchangeratesapp.ui.ratelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.domain.ExchangeRateDto

class ExchangeRateAdapter(
    private val data: MutableList<ExchangeRateDto>,
    private val onFavouriteClicked: (dto: ExchangeRateDto) -> Unit,
) :
    RecyclerView.Adapter<ExchangeRateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_exchange_rate, parent, false)
        return ExchangeRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        holder.bind(data[position], onFavouriteClicked)
    }

    override fun getItemCount() = data.size

    fun setData(newData: List<ExchangeRateDto>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    fun updateFavourites(favourites: List<ExchangeRateDto>) {
        favourites.forEach { fav ->
            val itemToUpdate = data.firstOrNull {
                fav.baseCurrency == it.baseCurrency
                        && fav.currencyCode == it.currencyCode
                        && fav.isFavourite != it.isFavourite
            }

            itemToUpdate?.let {
                it.isFavourite = fav.isFavourite
                notifyItemChanged(data.indexOf(itemToUpdate))
            }
        }
    }
}