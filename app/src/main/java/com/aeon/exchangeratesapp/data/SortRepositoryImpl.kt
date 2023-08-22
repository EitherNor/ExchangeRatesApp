package com.aeon.exchangeratesapp.data

import android.content.Context
import android.content.SharedPreferences
import com.aeon.exchangeratesapp.domain.sort.SortRepository
import com.aeon.exchangeratesapp.extensions.StateFlowExtensions.map
import com.aeon.exchangeratesapp.ui.sort.SortMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SortRepositoryImpl @Inject constructor(appContext: Context) : SortRepository {

    companion object {
        private const val SORT_PREFERENCES_FILE_NAME = "sortPreferences"
        private const val STRING_SORT_MODE = "STRING_SORT_MODE"
        private val DEFAULT_SORT_MODE = SortMode.NAME_ASC
    }

    private val _sortFlow = MutableStateFlow(DEFAULT_SORT_MODE)

    private val editor =
        appContext.getSharedPreferences(SORT_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit()
    private var preferences: SharedPreferences =
        appContext.getSharedPreferences(SORT_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    override suspend fun updateSortMode(sortMode: SortMode) {
        editor.apply {
            putString(STRING_SORT_MODE, sortMode.name)
            commit()
        }
        _sortFlow.update { sortMode }
    }

    override suspend fun getSortMode(): SortMode {
        return getSavedSortMode()
    }

    override fun observeSortMode(coroutineScope: CoroutineScope): Flow<SortMode> {
        return _sortFlow.map(coroutineScope) {
            if (it == DEFAULT_SORT_MODE) {
                getSavedSortMode()
            } else it
        }
    }

    private fun getSavedSortMode(): SortMode {
        val name = preferences.getString(
            STRING_SORT_MODE,
            DEFAULT_SORT_MODE.name
        )
            ?: DEFAULT_SORT_MODE.name
        return SortMode.valueOf(name)
    }
}