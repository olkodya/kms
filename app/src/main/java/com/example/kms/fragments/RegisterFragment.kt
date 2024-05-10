package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.adapter.RegisterAdapter
import com.example.kms.databinding.FragmentRegisterBinding
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.operations.RegisterViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterFragment : Fragment() {

    lateinit var adapter: RegisterAdapter
    private val viewModel by activityViewModels<RegisterViewModel> {
        viewModelFactory {
            initializer {
                RegisterViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


//        val operation = Operation(1, Key(
//            1,
//       ), Employee(1, "fsff", "sdsf", "dss", "daa"),
//            Shift(1, "sdsd", "dsd", Watch(1, 1), LoginDto("dad", "dadad"))
//        , "dad", "dada")
//
//        val list = (1..10).map {
//            Operation(1, Key(
//                1,
//                  ), Employee(1, "fsff", "sdsf", "dss", "daa"),
//                Shift(1, "sdsd", "dsd", Watch(1, 1), LoginDto("dad", "dadad"))
//                , "dad", "dada")
//        }
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        initRcView(binding)

//        adapter.submitList(list)


        viewModel.load()
        binding.emptyList.text = viewModel.uiState.value.operations.toString()
        viewModel.uiState
            .onEach { state ->
                adapter.submitList(state.operations)
                if (state.operations.isEmpty())
                    binding.emptyList.visibility = View.VISIBLE
                else
                    binding.emptyList.visibility = View.GONE
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)





        return binding.root

    }


    private fun initRcView(binding: FragmentRegisterBinding) {


        Log.d("SIZE", childFragmentManager.fragments.toString())

        adapter = RegisterAdapter { operationId ->
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomNavigationFragment_to_registerItemFragment,
                    bundleOf(RegisterItemFragment.OPERATION_ID_KEY to operationId)
                )
        }
        binding.list.adapter = adapter
    }
}