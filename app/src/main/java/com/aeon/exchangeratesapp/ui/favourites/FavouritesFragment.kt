package com.aeon.exchangeratesapp.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.extensions.FragmentExtensions.observeViewModel
import com.aeon.exchangeratesapp.ui.base.BaseRateListFragment
import com.aeon.exchangeratesapp.ui.base.ExchangeRatesUiState
import com.aeon.exchangeratesapp.utils.DelegateUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesFragment : BaseRateListFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: FavouritesViewModel by DelegateUtils.lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[FavouritesViewModel::class.java]
    }

    init {
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvExchangeRatesList.adapter = adapter
            srlSwipeRefreshLayout.isEnabled = false
        }

        observeViewModel {
            viewModel.favouritesFlow.collect {
                render(it)
            }
        }
    }

    private fun render(uiState: ExchangeRatesUiState) {
        with(binding) {
            when (uiState) {
                ExchangeRatesUiState.Loading -> {
                    rvExchangeRatesList.isVisible = false
                    gErrorGroup.isVisible = false

                    pbLoading.isVisible = true
                }
                is ExchangeRatesUiState.Success -> {
                    pbLoading.isVisible = false
                    gErrorGroup.isVisible = false

                    rvExchangeRatesList.isVisible = true
                    adapter.setData(uiState.data.exchangeRateDtoList.filter { it.isFavourite })
                }
                is ExchangeRatesUiState.Error -> {
                    rvExchangeRatesList.isVisible = false
                    pbLoading.isVisible = false

                    gErrorGroup.isVisible = true
                }
            }
        }
    }
}



