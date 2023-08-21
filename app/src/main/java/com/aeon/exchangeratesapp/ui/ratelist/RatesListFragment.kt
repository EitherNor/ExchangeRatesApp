package com.aeon.exchangeratesapp.ui.ratelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.ui.base.BaseRateListFragment
import com.aeon.exchangeratesapp.ui.base.ExchangeRatesUiState
import com.aeon.exchangeratesapp.utils.DelegateUtils.lazyUnsafe
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatesListFragment : BaseRateListFragment() {

    companion object {
        private val TAG = RatesListFragment::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: ExchangeRatesListViewModel by lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[ExchangeRatesListViewModel::class.java]
    }

    init {
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvExchangeRatesList.adapter = adapter
            tvErrorRetry.setOnClickListener { viewModel.onRetryClicked() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.exchangeRateDataFlow.collect {
                    render(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouritesFlow.filter { it is ExchangeRatesUiState.Success }.collect {
                    adapter.updateFavourites((it as ExchangeRatesUiState.Success).data.exchangeRateDtoList)
                }
            }
        }
    }

    private fun render(uiState: ExchangeRatesUiState) {
        with(binding) {
            when (uiState) {
                ExchangeRatesUiState.Loading -> {
                    Log.d(TAG, "render: loading")
                    rvExchangeRatesList.isVisible = false
                    gErrorGroup.isVisible = false

                    pbLoading.isVisible = true
                }

                is ExchangeRatesUiState.Success -> {
                    Log.d(TAG, "render: success, data size: ${uiState.data.exchangeRateDtoList.size}")
                    pbLoading.isVisible = false
                    gErrorGroup.isVisible = false

                    rvExchangeRatesList.isVisible = true
                    adapter.setData(uiState.data.exchangeRateDtoList)
                }

                is ExchangeRatesUiState.Error -> {
                    Log.d(TAG, "render: error ${uiState.throwable}")
                    pbLoading.isVisible = false
                    rvExchangeRatesList.isVisible = false

                    gErrorGroup.isVisible = true
                    tvErrorDescription.text = uiState.throwable?.message
                }
            }
        }
    }
}