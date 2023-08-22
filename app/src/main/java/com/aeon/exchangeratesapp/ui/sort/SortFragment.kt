package com.aeon.exchangeratesapp.ui.sort

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aeon.exchangeratesapp.App
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.databinding.FragmentSortBinding
import com.aeon.exchangeratesapp.di.ViewModelFactory
import com.aeon.exchangeratesapp.extensions.FragmentExtensions.observeViewModel
import com.aeon.exchangeratesapp.utils.DelegateUtils
import com.redmadrobot.extensions.viewbinding.viewBinding
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SortFragment : Fragment(R.layout.fragment_sort) {

    companion object {
        private val TAG = SortFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager) =
            fragmentManager.beginTransaction()
                .add(R.id.container, SortFragment(), TAG)
                .addToBackStack(TAG)
                .commit()
    }

    init {
        App.component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SortViewModel by DelegateUtils.lazyUnsafe {
        ViewModelProvider(
            this,
            viewModelFactory
        )[SortViewModel::class.java]
    }

    private val binding: FragmentSortBinding by viewBinding()

    private val adapter by DelegateUtils.lazyUnsafe {
        SortAdapter(SortMode.values().toList().map { SortModeUiModel(it) }) {
            viewModel.onSortClicked(it)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvSort.adapter = adapter
            root.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        }

        observeViewModel {
            viewModel.sortModeFlow.filter { it is SortModeUiState.Success }.collect {
                adapter.updateSelectedSortMode((it as SortModeUiState.Success).sortMode)
            }
        }
    }
}