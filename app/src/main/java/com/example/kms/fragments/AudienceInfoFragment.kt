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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kms.databinding.FragmentAudienceInfoBinding
import com.example.kms.network.api.AudienceApi
import com.example.kms.network.api.ImageApi
import com.example.kms.repository.AudienceRepositoryImpl
import com.example.kms.repository.ImageRepositoryImpl
import com.example.kms.viewmodels.AudienceInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AudienceInfoFragment : Fragment() {

    companion object {
        const val AUDIENCE_ID = "AUDIENCE_ID"
        const val AUDIENCE_IMAGE_ID = "AUDIENCE_IMAGE_ID"
    }

    private val viewModel by viewModels<AudienceInfoViewModel> {
        viewModelFactory {
            initializer {
                AudienceInfoViewModel(
                    AudienceRepositoryImpl(AudienceApi.INSTANCE),
                    ImageRepositoryImpl(ImageApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAudienceInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val navController = findNavController()
        binding.audienceToolbar.setupWithNavController(navController)

        if (arguments?.containsKey(AUDIENCE_ID) == true) {
            val audienceId: Int = requireArguments().getInt(AUDIENCE_ID)
            viewModel.getByID(audienceId)
            Log.d("ID", audienceId.toString())
        }

//        if (arguments?.containsKey(AUDIENCE_IMAGE_ID) == true) {
//            val audienceId: Int = requireArguments().getInt(AUDIENCE_IMAGE_ID)
//            viewModel.getAudiencePhoto(audienceId)
//            Log.d("ID", audienceId.toString())
//        } else {
//            viewModel.audience.onEach {
//                if (it.audience != null)
//            }.launchIn(viewLifecycleOwner.lifecycleScope)
//        }

//        viewLifecycleOwner.lifecycleScope.launch
//            viewModel.audience.collect {
//                if (it.audience != null) {
//
//                    if (arguments?.containsKey(AUDIENCE_IMAGE_ID) != true) {
//                        viewModel.getAudiencePhoto(
//                            viewModel.audience.value.audience?.audience_id ?: 0
//                        )
//                    }
//                }
//            }
//        }
        viewModel.audience.onEach {
            if (it.audience != null) {
                binding.audienceCapacity.text = it.audience.capacity.toString()
                binding.audienceNum.text = it.audience.number.toString()
                binding.audienceSignalization.text = it.audience.signalisation.toString()
                binding.audienceType.text = it.audience.audienceType.toString()

            }
            if (it.audiencePhoto != null) {
                Glide.with(requireContext()).load(it.audiencePhoto).fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.audiencePhoto)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

}