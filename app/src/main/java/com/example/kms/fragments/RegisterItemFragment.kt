package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.databinding.FragmentRegisterItemBinding


class RegisterItemFragment : Fragment() {

    companion object {
        const val OPERATION_ID_KEY = "CHECK_ID_KEY"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegisterItemBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.employeeToolbar.setupWithNavController(navController)

        return binding.root
    }
}