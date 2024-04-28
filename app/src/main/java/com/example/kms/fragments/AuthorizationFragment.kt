package com.example.kms.fragments

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentAuthorizationBinding
import com.example.kms.network.api.ShiftApi
import com.example.kms.network.api.UserApi
import com.example.kms.network.api.WatchApi
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.repository.NetworkUserRepository
import com.example.kms.repository.ShiftRepositoryImpl
import com.example.kms.repository.WatchRepositoryImpl
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthorizationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        val viewModel by activityViewModels<AuthorizationViewModel> {
            viewModelFactory {
                initializer {
                    AuthorizationViewModel(
                        NetworkUserRepository(UserApi.INSTANCE)
                    )
                }
            }
        }

        val profileViewModel by activityViewModels<ProfileViewModel> {
            viewModelFactory {
                initializer {
                    ProfileViewModel(
                        ShiftRepositoryImpl(ShiftApi.INSTANCE),
                        WatchRepositoryImpl(WatchApi.INSTANCE),
                    )
                }
            }
        }
        val login = binding.login.text
        val password = binding.password.text
        binding.signIn.setOnClickListener {
             viewModel.login(login.toString(), password.toString())
        }

            viewModel.state.onEach { state ->
                if(state.isSuccess) {
                    findNavController().navigate(R.id.action_authorizationFragment_to_bottomNavigationFragment)
                    profileViewModel.login()
                } else {

                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<AuthorizationViewModel> {
            viewModelFactory {
                initializer {
                    AuthorizationViewModel(
                        NetworkUserRepository(UserApi.INSTANCE)
                    )
                }
            }
        }

        viewModel.state.onEach { state ->
            if(state.err!=null) {
                Snackbar.make(view.findViewById(R.id.myCoordinatorLayout), "Неверный логин или пароль", Snackbar.LENGTH_LONG)
                    .show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }


}