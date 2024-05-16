package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.adapter.RegisterAdapter
import com.example.kms.databinding.FragmentRegisterBinding
import com.example.kms.model.Operation
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.utils.Date
import com.example.kms.viewmodels.operations.RegisterViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

class RegisterFragment : Fragment() {
    lateinit var searchView: SearchView
    lateinit var adapter: RegisterAdapter
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<RegisterViewModel> {
        viewModelFactory {
            initializer {
                RegisterViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

//        val operation = Operation(1, Key(
//            1,
//       ), Employee(1, "fsff", "sdsf", "dss", "daa"),
//            Shift(1, "sdsd", "dsd", Watch(1, 1), LoginDto("dad", "dadad"))
//        , "dad", "dada")
//
//        val list = (1..10).map {
//            Operation(1, Key(
//                1,
//                  ), Employee(1, "fsff", "sdsf", "dss", "daa"),
//                Shift(1, "sdsd", "dsd", Watch(1, 1), LoginDto("dad", "dadad"))
//                , "dad", "dada")
//        }

        initRcView(binding)

//        adapter.submitList(list)

        searchView = binding.searchView
        viewModel.load()
        binding.emptyList.text = viewModel.uiState.value.operations.toString()
        binding.chipDate.setOnClickListener {
            setDatePicker()
        }

        binding.chipDate.setOnCloseIconClickListener {
            Toast.makeText(requireContext(), "ppp", Toast.LENGTH_LONG).show()
            adapter.submitList(viewModel.uiState.value.operations)
            binding.chipDate.text = "Смена"
        }
        viewModel.uiState
            .onEach { state ->
                adapter.submitList(state.operations)
                if (state.operations.isEmpty())
                    binding.emptyList.visibility = View.VISIBLE
                else
                    binding.emptyList.visibility = View.GONE
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })





        return binding.root

    }


    private fun filterList(query: String?) {
        val filteredList = emptyList<Operation>().toMutableList()
        if (query != null) {
            for (i in viewModel.uiState.value.operations) {
                if (i.give_date_time.toString().lowercase(Locale.ROOT)
                        .contains(query.toString())
                ) {
                    println("dsdsd1")
                    filteredList += i
                }
            }
            println(filteredList)
            adapter.submitList(filteredList)
        } else {
            adapter.submitList(filteredList)
            //adapter.submitList(viewModel.uiState.value.operations)
        }
    }


    private fun initRcView(binding: FragmentRegisterBinding) {


        Log.d("SIZE", childFragmentManager.fragments.toString())

        adapter = RegisterAdapter { operationId ->
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomNavigationFragment_to_registerItemFragment,
                    bundleOf(RegisterItemFragment.OPERATION_ID_KEY to operationId)
                )
        }
        binding.list.adapter = adapter
    }


    private fun setDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату смены")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        datePicker.show(childFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            val date = Date.convertTimeToDate(it)
            binding.chipDate.text = date
            val filteredList = emptyList<Operation>().toMutableList()
            for (i in viewModel.uiState.value.operations) {
                if (i.give_date_time.toString().lowercase(Locale.ROOT)
                        .contains(date)
                ) {
                    println("dsdsd1")
                    filteredList += i
                }
            }
            println(filteredList)
            adapter.submitList(filteredList)
        }

    }

}