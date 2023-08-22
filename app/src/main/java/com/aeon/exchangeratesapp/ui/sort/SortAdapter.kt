package com.aeon.exchangeratesapp.ui.sort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aeon.exchangeratesapp.R

class SortAdapter(
    private val data: List<SortModeUiModel>,
    private val onSortModeClicked: (sortMode: SortMode) -> Unit,
) : RecyclerView.Adapter<SortViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_sort, parent, false)
        return SortViewHolder(view)
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        holder.bind(
            sortModel = data[position],
            isFirst = position == 0,
            isLast = position == data.size - 1,
            onSortClicked = onSortModeClicked
        )
    }

    override fun getItemCount() = data.size

    fun updateSelectedSortMode(sortMode: SortMode) {
        data.forEach {
            it.isSelected = it.sortMode == sortMode
        }
        notifyDataSetChanged()
    }
}