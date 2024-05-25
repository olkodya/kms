package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.OperationsRepositoryImpl
import com.example.kms.utils.Converter
import com.example.kms.viewmodels.register.ShiftRegisterViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


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
        viewModel.load()
        searchView = binding.searchView
        binding.emptyList.text = viewModel.uiState.value.operations.toString()
        binding.chipDate.setOnClickListener {
            binding.chipDate.isChecked = false
            setDatePicker()
        }

        binding.chipDate.setOnCloseIconClickListener {
            viewModel.updateDate("")
            viewModel.filterList(viewModel.query.value, "")
            binding.chipDate.text = "Смена"
            binding.chipDate.isCloseIconVisible = false
            binding.chipDate.isCheckable = true
            binding.chipDate.isChecked = false
        }

        if (viewModel.date.value != "") {
            binding.chipDate.isChecked = true
            binding.chipDate.isCheckable = false
            binding.chipDate.isCloseIconVisible = true
            binding.chipDate.text = viewModel.date.value
        }

        viewModel.filteredList.onEach {
            adapter.submitList(viewModel.filteredList.value)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        searchView.setQuery(viewModel.query.value, false)
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
        viewModel.updateQuery(query ?: "")
        viewModel.filterList(query, viewModel.date.value)
        viewModel.filteredList.onEach {
            adapter.submitList(viewModel.filteredList.value)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
            val date = Converter.convertTimeToDate(it)
            binding.chipDate.text = date
            viewModel.updateDate(date)
            viewModel.filterList(viewModel.query.value, date)
            binding.chipDate.isCloseIconVisible = true
            binding.chipDate.isChecked = true
            binding.chipDate.isCheckable = false
        }
    }

}