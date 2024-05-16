package com.example.kms.fragments

import SignalisationRegisterAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentSignalisationRegisterBinding
import com.example.kms.network.api.AudienceApi
import com.example.kms.repository.AudienceRepositoryImpl
import com.example.kms.viewmodels.register.SignalisationRegisterViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class SignalisationRegisterFragment : Fragment() {

    lateinit var adapter: SignalisationRegisterAdapter
    private val viewModel by viewModels<SignalisationRegisterViewModel> {
        viewModelFactory {
            initializer {
                SignalisationRegisterViewModel(
                    AudienceRepositoryImpl(AudienceApi.INSTANCE)
                )
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignalisationRegisterBinding.inflate(inflater, container, false)
        initRcView(binding)
        // Inflate the layout for this fragment
        viewModel.uiState
            .onEach { state ->
                adapter.submitList(state.audiences)
//                if (state.audiences.isEmpty())
//                    binding.emptyList.visibility = View.VISIBLE
//                else
//                    binding.emptyList.visibility = View.GONE
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }


    private fun initRcView(binding: FragmentSignalisationRegisterBinding) {


        Log.d("SIZE", childFragmentManager.fragments.toString())

        adapter = SignalisationRegisterAdapter { audienceId ->
            requireParentFragment().requireParentFragment().requireParentFragment()
                .requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomNavigationFragment_to_audienceInfoFragment,
                    bundleOf(RegisterItemFragment.OPERATION_ID_KEY to audienceId)
                )
        }
        binding.list.adapter = adapter
    }


}