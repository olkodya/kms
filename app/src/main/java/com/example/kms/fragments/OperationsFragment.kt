package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentOperationsBinding
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OperationsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val viewModel by activityViewModels<OperationsViewModel> ()

        val binding = FragmentOperationsBinding.inflate(inflater, container, false)

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if(isChecked) {
                when(checkedId){
                    R.id.button1 -> viewModel.checkGive()
                    R.id.button2 ->  viewModel.checkTake()
                }
            }

        }

        return binding.root
    }
}