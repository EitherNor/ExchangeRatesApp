package com.aeon.exchangeratesapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentContentContainerBinding
import com.aeon.exchangeratesapp.ui.ExchangeRatesTabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.redmadrobot.extensions.viewbinding.viewBinding

class ContainerFragment : Fragment(R.layout.fragment_content_container) {

    companion object {
        val TAG: String = RatesListFragment::class.java.simpleName
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
        }
    }
}