package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentEmployeeInfoBinding


class EmployeeInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEmployeeInfoBinding.inflate(inflater, container, false)

        binding.continueBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_employeeInfoFragment2_to_signaturePadFragment)

        }
        // Inflate the layout for this fragment
        return binding.root
    }
}