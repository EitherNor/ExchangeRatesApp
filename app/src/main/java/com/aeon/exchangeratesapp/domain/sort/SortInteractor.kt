package com.aeon.exchangeratesapp.domain.sort

import com.aeon.exchangeratesapp.ui.sort.SortMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface SortInteractor {

    suspend fun updateSortMode(sortMode: SortMode)

    suspend fun getSortMode(): SortMode

    fun observeSortMode(coroutineScope: CoroutineScope): Flow<SortMode>
}