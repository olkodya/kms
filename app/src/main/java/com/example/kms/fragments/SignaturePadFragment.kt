package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentSignaturePadBinding
import com.example.kms.viewmodels.operations.OperationsViewModel
import com.github.gcacace.signaturepad.utils.SignaturePadBindingAdapter
import com.github.gcacace.signaturepad.views.SignaturePad


class SignaturePadFragment : Fragment() {
    private lateinit var signaturePad: SignaturePad
    private val operationsViewModel by activityViewModels<OperationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignaturePadBinding.inflate(inflater, container, false)
        signaturePad = binding.signaturePad
        binding.clearBtn.setOnClickListener { 
            if(!signaturePad.isEmpty){
                signaturePad.clear()
            }else {
                Toast.makeText(requireContext(), "Signature pad is already empty", Toast.LENGTH_LONG).show()
            }
        }

        binding.submitBtn.setOnClickListener {
            if(!signaturePad.isEmpty){
                binding.image.setImageBitmap(signaturePad.signatureBitmap)
            } else {
                Toast.makeText(requireContext(), "Signature pad is already empty", Toast.LENGTH_LONG).show()

            }
        }

        binding.finish.setOnClickListener {
            operationsViewModel.checkGive()
            findNavController().navigate(R.id.action_signaturePadFragment_to_bottomNavigationFragment2)

        }
        return binding.root
    }
}