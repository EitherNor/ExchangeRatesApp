package com.aeon.exchangeratesapp.ui.sort

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.aeon.exchangeratesapp.R

enum class SortMode(@StringRes val title: Int, @DrawableRes val image: Int, @DrawableRes val selectedImage: Int) {
    NAME_ASC(R.string.tvNameSortTitle, R.drawable.ic_sort_asc, R.drawable.ic_sort_asc_selected),
    NAME_DESC(R.string.tvNameSortTitle, R.drawable.ic_sort_desc, R.drawable.ic_sort_desc_selected),
    VALUE_ASC(R.string.tvValueSortTitle, R.drawable.ic_sort_asc, R.drawable.ic_sort_asc_selected),
    VALUE_DESC(R.string.tvValueSortTitle, R.drawable.ic_sort_desc, R.drawable.ic_sort_desc_selected),
}