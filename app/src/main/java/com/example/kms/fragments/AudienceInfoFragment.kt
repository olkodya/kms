package com.example.kms.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.databinding.FragmentAudienceInfoBinding

class AudienceInfoFragment : Fragment() {

    companion object {
        const val AUDIENCE_ID = "AUDIENCE_ID"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAudienceInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val navController = findNavController()
        binding.audienceToolbar.setupWithNavController(navController)
        return binding.root
    }

}