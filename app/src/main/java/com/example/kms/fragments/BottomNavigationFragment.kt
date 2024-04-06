package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentBottomNavigationBinding
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomNavigationFragment : Fragment() {
    private val operationsViewModel by activityViewModels<OperationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        val navController =  requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
        operationsViewModel.giveKey.onEach {
            if(!it) {
                findNavController().navigate(R.id.action_bottomNavigationFragment_to_employeeInfoFragment2)
            }   }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root

    }
}

