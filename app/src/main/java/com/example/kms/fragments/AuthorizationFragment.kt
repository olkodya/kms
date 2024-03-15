package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentAuthorizationBinding
import com.example.kms.network.api.UserApi
import com.example.kms.viewmodels.AuthorizationViewModel
import com.example.kms.repository.NetworkUserRepository
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val viewModel by viewModels<AuthorizationViewModel> {
            viewModelFactory {
                initializer {
                    AuthorizationViewModel(
                        NetworkUserRepository(UserApi.INSTANCE)
                    )
                }
            }
        }


        val login = binding.login.text
        val password = binding.password.text

        binding.signIn.setOnClickListener {
             viewModel.login(login.toString(), password.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                if(state.isSuccess) {
                    findNavController().navigate(R.id.action_authorizationFragment_to_bottomNavigationFragment)
                } else {
                }
            }
        }


        return binding.root
    }
}