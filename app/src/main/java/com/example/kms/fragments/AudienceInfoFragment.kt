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
import com.example.kms.databinding.FragmentAudienceInfoBinding
import com.example.kms.network.api.AudienceApi
import com.example.kms.repository.AudienceRepositoryImpl
import com.example.kms.viewmodels.AudienceInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AudienceInfoFragment : Fragment() {

    companion object {
        const val AUDIENCE_ID = "AUDIENCE_ID"
    }

    private val viewModel by viewModels<AudienceInfoViewModel> {
        viewModelFactory {
            initializer {
                AudienceInfoViewModel(
                    AudienceRepositoryImpl(AudienceApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAudienceInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val navController = findNavController()
        binding.audienceToolbar.setupWithNavController(navController)

        if (arguments?.containsKey(AUDIENCE_ID) == true) {
            val audienceId: Int = requireArguments().getInt(AUDIENCE_ID)
            viewModel.getByID(audienceId)
            Log.d("ID", audienceId.toString())
        }


        viewModel.audience.onEach {
            if (it.audience != null) {
                binding.audienceCapacity.text = it.audience?.capacity.toString()
                binding.audienceNum.text = it.audience?.number.toString()
                binding.audienceSignalization.text = it.audience?.signalisation.toString()
                binding.audienceType.text = it.audience?.audienceType.toString()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

}