package com.demo.weather.history.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.demo.weather.R
import com.demo.weather.databinding.HistoryFragmentBinding

class HistoryFragment: Fragment(R.layout.history_fragment) {

    private lateinit var binding: HistoryFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HistoryFragmentBinding.bind(view)

    }
}