package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentScanKeyBinding
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ScanKeyFragment : Fragment() {
    private val operationsViewModel by activityViewModels<OperationsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentScanKeyBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        operationsViewModel.checkKey()
        operationsViewModel.scanned.onEach {
            if (it)
                findNavController().navigate(R.id.action_scanKeyFragment2_to_signaturePadFragment)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}