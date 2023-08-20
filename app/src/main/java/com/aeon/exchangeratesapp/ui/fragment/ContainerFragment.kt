package com.aeon.exchangeratesapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentContentContainerBinding
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.ui.ExchangeRatesTabAdapter
import com.aeon.exchangeratesapp.ui.viewmodel.ContainerViewModel
import com.aeon.exchangeratesapp.utils.DelegateUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.redmadrobot.extensions.viewbinding.viewBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContainerFragment : Fragment(R.layout.fragment_content_container) {

    companion object {
        val TAG: String = RatesListFragment::class.java.simpleName
    }

    init {
        App.component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ContainerViewModel by DelegateUtils.lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[ContainerViewModel::class.java]
    }

    private val binding: FragmentContentContainerBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            vpRatesContent.adapter = ExchangeRatesTabAdapter(childFragmentManager, lifecycle)
            TabLayoutMediator(tlBottomTabs, vpRatesContent) { tab, position ->
                tab.text = requireContext().getString(
                    ExchangeRatesTabAdapter.ExchangeRatesTab.fromPosition(position).titleStringRes
                )
            }.attach()
            clCurrencySelectorContainer.setOnClickListener {
                CurrencySelectorDialogFragment.show(
                    parentFragmentManager
                )
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.observeBaseCurrency().collect { tvBaseCurrencyCode.text = it }
                }
            }
        }
    }
}