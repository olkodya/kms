package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentRegisterItemBinding
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.RegisterItemViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RegisterItemFragment : Fragment() {

    companion object {
        const val OPERATION_ID_KEY = "CHECK_ID_KEY"
    }

    private val viewModel by viewModels<RegisterItemViewModel> {
        viewModelFactory {
            initializer {
                RegisterItemViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE)
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.containsKey(OPERATION_ID_KEY) == true) {
            val operationId: Int = requireArguments().getInt(OPERATION_ID_KEY)
            viewModel.getOperation(operationId)
            Log.d("ID", operationId.toString())
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterItemBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        viewModel.uiState.onEach {
            binding.giveDate.text = it.operation?.give_date_time
            binding.returnDate.text = it.operation?.return_date_time
        }.launchIn(viewLifecycleOwner.lifecycleScope)
//        binding.giveDate.text = viewModel.uiState.value.operation?.give_date_time
//        binding.returnDate.text = viewModel.uiState.value.operation?.return_date_time
        binding.employeeCard.setOnClickListener {
            findNavController().navigate(R.id.action_registerItemFragment_to_employeeInfoFragment2)
        }

        binding.audienceCard.setOnClickListener {
            findNavController().navigate(R.id.action_registerItemFragment_to_audienceInfoFragment)
        }
        return binding.root
    }
}