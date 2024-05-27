package com.example.kms.fragments.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kms.R
import com.example.kms.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.button2 -> childFragmentManager.beginTransaction()
                        .replace(R.id.container, SignalisationRegisterFragment()).commit()

                    R.id.button1 -> childFragmentManager.beginTransaction()
                        .replace(R.id.container, ShiftRegisterFragment()).commit()
                }
            }
        }
        return binding.root

    }
}