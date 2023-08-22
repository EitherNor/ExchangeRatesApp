package com.aeon.exchangeratesapp.ui.currency

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentCurrencySelectorDialogBinding
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.extensions.FragmentExtensions.observeViewModel
import com.aeon.exchangeratesapp.utils.DelegateUtils
import com.redmadrobot.extensions.viewbinding.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class CurrencySelectorDialogFragment : Fragment(R.layout.fragment_currency_selector_dialog) {

    companion object {
        private val TAG = CurrencySelectorDialogFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager) =
            fragmentManager.beginTransaction()
                .add(R.id.container, CurrencySelectorDialogFragment(), TAG)
                .addToBackStack(TAG)
                .commit()
    }

    init {
        App.component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CurrencyViewModel by DelegateUtils.lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[CurrencyViewModel::class.java]
    }

    private val binding: FragmentCurrencySelectorDialogBinding by viewBinding()
    private val adapter by DelegateUtils.lazyUnsafe {
        CurrencyAdapter(
            mutableListOf()
        ) {
            viewModel.onCurrencyCodeClicked(it)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvCurrencyList.adapter = adapter
            rvCurrencyList.layoutManager = GridLayoutManager(requireContext(), 3)
            ivClose.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        }
        binding.rvCurrencyList.adapter = adapter

        observeViewModel {
            viewModel.currencyDataFlow.collect { render(it) }
        }
    }

    private fun render(uiState: CurrencyUiState) {
        with(binding) {
            when (uiState) {
                CurrencyUiState.Loading -> {
                    rvCurrencyList.isVisible = false
                    tvErrorText.isVisible = false

                    pbLoading.isVisible = true
                }

                is CurrencyUiState.Success -> {
                    adapter.setData(uiState.data)

                    tvErrorText.isVisible = false
                    pbLoading.isVisible = false

                    rvCurrencyList.isVisible = true
                }

                is CurrencyUiState.Error -> {
                    pbLoading.isVisible = false
                    rvCurrencyList.isVisible = false

                    tvErrorText.isVisible = true
                }
            }
        }
    }
}