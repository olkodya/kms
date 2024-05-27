package com.example.kms.fragments.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.kms.R
import com.example.kms.databinding.FragmentOperationsBinding
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class OperationsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel by activityViewModels<OperationsViewModel>()

        val binding = FragmentOperationsBinding.inflate(inflater, container, false)

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button1 -> viewModel.checkEmployee()
                    R.id.button2 -> viewModel.checkKey()
                }
            }
        }
        viewModel.employee.onEach {
            if (it) {
                binding.toggleButton.check(R.id.button1)
            } else {
                binding.toggleButton.check(R.id.button2)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

//        binding.continueBtn.setOnClickListener {
//            viewModel.setScanned()
//        }
        return binding.root
    }


}