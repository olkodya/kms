package com.example.kms.fragments.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentScanKeyBinding
import com.example.kms.fragments.operations.SignaturePadFragment
import com.example.kms.viewmodels.operations.OperationsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ScanKeyFragment : Fragment() {



    companion object {
        const val EMPLOYEE_ID = "EMPLOYEE_ID"
        const val OPERATION = "OPERATION"
        var employeeId: Int = 0
    }

    private val operationsViewModel by activityViewModels<OperationsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentScanKeyBinding.inflate(inflater, container, false)
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        operationsViewModel.scanned.onEach {
            if (it) {
                if (arguments?.containsKey(EMPLOYEE_ID) == true) {
                    val employee: Int = requireArguments().getInt(EMPLOYEE_ID)
                    employeeId = employee
                    Log.d("scan", employeeId.toString())
                }
                findNavController().navigate(
                    R.id.action_scanKeyFragment2_to_signaturePadFragment,
                    bundleOf(
                        SignaturePadFragment.EMPLOYEE_ID to employeeId,
                        SignaturePadFragment.KEY_ID to operationsViewModel.uiState.value.key?.key_id,
                        SignaturePadFragment.OPERATION to true
                    )
                )
                operationsViewModel.reset()
            }


        }.launchIn(viewLifecycleOwner.lifecycleScope)
        return binding.root
    }
}