package com.demo.weather.common.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.demo.weather.R
import com.demo.weather.common.helper.ErrorParser
import com.demo.weather.common.helper.fadeTo
import com.demo.weather.databinding.ErrorFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ErrorFragment: DialogFragment() {

    @Inject
    lateinit var errorParser: ErrorParser

    private lateinit var binding: ErrorFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorFragmentBinding.inflate(layoutInflater)
        val args = ErrorFragmentArgs.fromBundle(requireArguments())
        setup(args)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = ErrorFragmentArgs.fromBundle(requireArguments())
        return dialogSetup(args)
    }

    private fun setup(args: ErrorFragmentArgs) = binding.apply {
        val state = errorParser.parse(args.exception)
        message.text = state.msg
        title.text = state.title
        positiveBtn.text = state.posTitle ?: getString(R.string.okay)
        positiveBtn.setOnClickListener {
            if (state.intent != null) {
                startPermissionIntent(state.intent)
            } else findNavController().popBackStack()
        }
        if (state.negTitle != null) {
            negativeBtn.fadeTo(true)
            negativeBtn.text = state.negTitle
            negativeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun dialogSetup(args: ErrorFragmentArgs) = AlertDialog.Builder(
        requireContext(), androidx.appcompat.R.style.AlertDialog_AppCompat_Light
    ).apply {
        setView(binding.root)
        setup(args)
    }.create()

    private fun startPermissionIntent(intent: Intent) {
        val newIntent = if (intent.data != null) Intent(intent.action, intent.data)
         else Intent(intent.action)
        startActivity(newIntent)
    }

}