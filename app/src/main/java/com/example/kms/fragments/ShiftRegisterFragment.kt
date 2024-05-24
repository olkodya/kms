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
import com.example.kms.databinding.FragmentShiftRegisterBinding
import com.example.kms.lists.shifts.RegisterAdapter
import com.example.kms.model.Operation
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.utils.Date
import com.example.kms.viewmodels.register.ShiftRegisterViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale


class ShiftRegisterFragment : Fragment() {

    lateinit var searchView: SearchView
    lateinit var adapter: RegisterAdapter
    private var _binding: FragmentShiftRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ShiftRegisterViewModel> {
        viewModelFactory {
            initializer {
                ShiftRegisterViewModel(
                    OperationsRepositoryImpl(OperationApi.INSTANCE)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShiftRegisterBinding.inflate(inflater, container, false)

        initRcView(binding)
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
        val set = emptySet<Operation>().toMutableSet().toMutableSet()
        var filteredList = emptyList<Operation>().toMutableList()
        if (query != null) {
            for (i in viewModel.uiState.value.operations) {
                if ((i.employee?.first_name.toString().lowercase(Locale.ROOT)
                        .contains(
                            query.toString().lowercase(Locale.ROOT)
                        )) || (i.employee?.second_name.toString().lowercase(Locale.ROOT)
                        .contains(
                            query.toString().lowercase(Locale.ROOT)
                        )) || (i.employee?.middle_name.toString().lowercase(Locale.ROOT)
                        .contains(query.toString().lowercase(Locale.ROOT)))
                    || (i.key.audience.number.toString().lowercase(Locale.ROOT)
                        .contains(query.toString().lowercase(Locale.ROOT)))
                ) {
                    set += i
                }
            }
            filteredList = set.toMutableList()
            println(filteredList)
            adapter.submitList(filteredList)
        } else {
            adapter.submitList(filteredList)
            //adapter.submitList(viewModel.uiState.value.operations)
        }
    }


    private fun initRcView(binding: FragmentShiftRegisterBinding) {
        Log.d("SIZE", childFragmentManager.fragments.toString())
        adapter = RegisterAdapter { operationId ->
            requireParentFragment().requireParentFragment().requireParentFragment()
                .requireParentFragment().findNavController()
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
                    filteredList += i
                }
            }
            println(filteredList)
            adapter.submitList(filteredList)
        }

    }

}