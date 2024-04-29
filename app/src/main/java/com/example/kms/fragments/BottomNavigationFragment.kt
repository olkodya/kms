package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentBottomNavigationBinding
import com.example.kms.mapper.ShiftUiModelMapper
import com.example.kms.network.api.ShiftApi
import com.example.kms.network.api.WatchApi
import com.example.kms.repository.ShiftRepositoryImpl
import com.example.kms.repository.WatchRepositoryImpl
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomNavigationFragment : Fragment() {
    private val operationsViewModel by activityViewModels<OperationsViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel> {
        viewModelFactory {
            initializer {
                ProfileViewModel(
                    ShiftRepositoryImpl(ShiftApi.INSTANCE),
                    WatchRepositoryImpl(WatchApi.INSTANCE),
                    ShiftUiModelMapper()
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()
        binding.bottomNavigation.setupWithNavController(navController)
        operationsViewModel.giveKey.onEach {
            if (!it) {
                findNavController().navigate(R.id.action_bottomNavigationFragment_to_employeeInfoFragment2)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        //profileViewModel

        profileViewModel.logout.onEach {
            if (it) {
                findNavController().navigate(R.id.action_bottomNavigationFragment_to_authorizationFragment4)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root

    }
}

