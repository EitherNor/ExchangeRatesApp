package com.aeon.exchangeratesapp.domain.sort

import com.aeon.exchangeratesapp.ui.sort.SortMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortInteractorImpl @Inject constructor(private val sortRepository: SortRepository) :
    SortInteractor {

    override suspend fun updateSortMode(sortMode: SortMode) =
        sortRepository.updateSortMode(sortMode)

    override suspend fun getSortMode() = sortRepository.getSortMode()

    override fun observeSortMode(coroutineScope: CoroutineScope): Flow<SortMode> =
        sortRepository.observeSortMode(coroutineScope)
}
