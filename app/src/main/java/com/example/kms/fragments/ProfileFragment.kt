package com.example.kms.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)

//        val navController =
//            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()
        val toolbar = binding.profileToolBar
//        toolbar.setupWithNavController(navController)

        val item = toolbar.menu.findItem(R.id.action_logout)



        item.setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_LONG).show()
//            findNavController().navigate(R.id.action_profileFragment_to_authorizationFragment)
            true
        }
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}