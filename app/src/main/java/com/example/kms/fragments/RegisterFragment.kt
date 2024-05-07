package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kms.R
import com.example.kms.adapter.RegisterAdapter
import com.example.kms.databinding.FragmentRegisterBinding
import com.example.kms.model.Audience
import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.LoginDto
import com.example.kms.model.Operation
import com.example.kms.model.Shift
import com.example.kms.model.Watch
import com.example.kms.network.api.OperationApi
import com.example.kms.network.api.UserApi
import com.example.kms.repository.NetworkUserRepository
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.viewmodels.operations.RegisterViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterFragment : Fragment() {

    lateinit var adapter: RegisterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel by activityViewModels<RegisterViewModel> {
            viewModelFactory {
                initializer {
                    RegisterViewModel(
                        OperationsRepositoryImpl(OperationApi.INSTANCE)
                    )
                }
            }
        }

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
        val binding = FragmentRegisterBinding.inflate(inflater,container, false)

        initRcView(binding)

//        adapter.submitList(list)





        viewModel.uiState
            .onEach {
                state ->
                    adapter.submitList(state.operations)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)





        return binding.root

    }


    private fun initRcView(binding: FragmentRegisterBinding) {
        adapter = RegisterAdapter()
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }
}