package com.aeon.exchangeratesapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.aeon.exchangeratesapp.ui.adapter.CurrencyAdapter
import com.aeon.exchangeratesapp.ui.state.CurrencyUiState
import com.aeon.exchangeratesapp.ui.viewmodel.CurrencyViewModel
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currencyDataFlow.collect { render(it) }
            }
        }
    }

    private fun render(uiState: CurrencyUiState) {
        when (uiState) {
            CurrencyUiState.Loading -> Toast.makeText(
                requireContext(),
                "Loading...",
                Toast.LENGTH_LONG
            ).show()

            is CurrencyUiState.Success -> adapter.setData(uiState.data)
            is CurrencyUiState.Error -> Toast.makeText(
                requireContext(),
                "Error!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}