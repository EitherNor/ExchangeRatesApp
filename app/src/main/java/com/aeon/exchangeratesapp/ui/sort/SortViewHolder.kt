package com.aeon.exchangeratesapp.ui.sort

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.utils.DelegateUtils.lazyUnsafe

class SortViewHolder(view: View) : ViewHolder(view) {

    private val tvSortTitle: TextView by lazyUnsafe { itemView.findViewById(R.id.tvSortTitle) }
    private val ivSortImage: ImageView by lazyUnsafe { itemView.findViewById(R.id.ivSortImage) }
    private val vDivider: View by lazyUnsafe { itemView.findViewById(R.id.vDivider) }

    fun bind(
        sortModel: SortModeUiModel,
        isFirst: Boolean,
        isLast: Boolean,
        onSortClicked: (mode: SortMode) -> Unit,
    ) {
        tvSortTitle.apply {
            text = itemView.resources.getString(sortModel.sortMode.title)
            setTextColor(
                if (sortModel.isSelected) resources.getColor(R.color.orange_sun)
                else resources.getColor(R.color.white_ghost)
            )
        }
        ivSortImage.setImageResource(
            if (sortModel.isSelected) sortModel.sortMode.selectedImage
            else sortModel.sortMode.image)
        itemView.setOnClickListener { onSortClicked(sortModel.sortMode) }

        if (isFirst) {
            itemView.setBackgroundResource(R.drawable.shape_sort_list_item_background_first)
            vDivider.isVisible = true
        } else if (isLast) {
            itemView.setBackgroundResource(R.drawable.shape_sort_list_item_background_last)
            vDivider.isVisible = false
        } else {
            itemView.setBackgroundResource(R.color.orange_flame)
            vDivider.isVisible = true
        }
    }
}