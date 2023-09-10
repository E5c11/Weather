package com.demo.weather.history.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.weather.R
import com.demo.weather.common.helper.Resource
import com.demo.weather.common.helper.collectIn
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.databinding.HistoryFragmentBinding
import com.demo.weather.history.component.HistoryListAdapter
import com.demo.weather.history.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment: Fragment(R.layout.history_fragment) {

    private lateinit var binding: HistoryFragmentBinding
    private val viewModel: HistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HistoryFragmentBinding.bind(view)

        val historyAdapter = HistoryListAdapter()

        binding.apply {
            recyclerView.adapter = historyAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            Log.d("myT", "onViewCreated: ")
            viewModel.getRecentLocationsWeather().collectIn(viewLifecycleOwner) {
                Log.d("myT", "onViewCreated: $it")
                emptyText.fadeTo(Resource.Status.LOADING == it.status || Resource.Status.ERROR == it.status)
                recyclerView.fadeTo(Resource.Status.SUCCESS == it.status)
                historyAdapter.submitList(it.data)
            }
        }
    }
}