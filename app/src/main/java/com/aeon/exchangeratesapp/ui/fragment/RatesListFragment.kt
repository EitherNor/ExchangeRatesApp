package com.aeon.exchangeratesapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentExchangeRatesListBinding
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.ui.adapter.ExchangeRateAdapter
import com.aeon.exchangeratesapp.ui.state.ExchangeRatesUiState
import com.aeon.exchangeratesapp.ui.viewmodel.ExchangeRatesListViewModel
import com.aeon.exchangeratesapp.utils.DelegateUtils.lazyUnsafe
import com.redmadrobot.extensions.viewbinding.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatesListFragment : Fragment(R.layout.fragment_exchange_rates_list) {

    companion object {
        private val TAG = RatesListFragment::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExchangeRatesListViewModel by lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[ExchangeRatesListViewModel::class.java]
    }

    private val binding: FragmentExchangeRatesListBinding by viewBinding()

    private lateinit var adapter: ExchangeRateAdapter

    init {
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExchangeRateAdapter(mutableListOf())

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
    }

    private fun render(uiState: ExchangeRatesUiState) {
        with(binding) {
            when (uiState) {
                ExchangeRatesUiState.Loading -> {
                    Log.d(TAG, "render: loading")
                    rvExchangeRatesList.isVisible = false
                    tvErrorText.isVisible = false
                    tvErrorRetry.isVisible = false

                    pbLoading.isVisible = true
                }

                is ExchangeRatesUiState.Success -> {
                    Log.d(TAG, "render: success, data size: ${uiState.data.size}")
                    pbLoading.isVisible = false
                    tvErrorText.isVisible = false
                    tvErrorRetry.isVisible = false

                    rvExchangeRatesList.isVisible = true
                    adapter.setData(uiState.data)
                }

                is ExchangeRatesUiState.Error -> {
                    Log.d(TAG, "render: error ${uiState.throwable}")
                    pbLoading.isVisible = false
                    rvExchangeRatesList.isVisible = false

                    tvErrorText.isVisible = true
                    tvErrorRetry.isVisible = true
                }
            }
        }
    }
}