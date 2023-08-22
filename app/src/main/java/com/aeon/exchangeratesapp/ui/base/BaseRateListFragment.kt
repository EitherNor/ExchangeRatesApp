package com.aeon.exchangeratesapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentExchangeRatesListBinding
import com.aeon.exchangeratesapp.ui.ratelist.ExchangeRateAdapter
import com.redmadrobot.extensions.viewbinding.viewBinding

abstract class BaseRateListFragment : Fragment(R.layout.fragment_exchange_rates_list) {

    protected lateinit var adapter: ExchangeRateAdapter

    protected abstract val viewModel: BaseViewModel

    protected val binding: FragmentExchangeRatesListBinding by viewBinding()

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ExchangeRateAdapter(mutableListOf()) { viewModel.onFavouriteClicked(it) }
    }
}
