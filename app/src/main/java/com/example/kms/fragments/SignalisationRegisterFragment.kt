package com.example.kms.fragments

import SignalisationRegisterAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.example.kms.databinding.FragmentSignalisationRegisterBinding
import com.example.kms.network.api.AudienceApi
import com.example.kms.repository.AudienceRepositoryImpl
import com.example.kms.viewmodels.register.SignalisationRegisterViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class SignalisationRegisterFragment : Fragment() {
    lateinit var searchView: SearchView

    lateinit var adapter: SignalisationRegisterAdapter
    private val viewModel by activityViewModels<SignalisationRegisterViewModel> {
        viewModelFactory {
            initializer {
                SignalisationRegisterViewModel(
                    AudienceRepositoryImpl(AudienceApi.INSTANCE)
                )
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignalisationRegisterBinding.inflate(inflater, container, false)
        searchView = binding.searchView

        initRcView(binding)
        viewModel.load()
        binding.chipDate.setOnClickListener {
            binding.chipDate.isChecked = false
            setFilters(binding)
        }


        binding.chipDate.setOnCloseIconClickListener {
            binding.chipDate.isCheckable = true
            binding.chipDate.isChecked = false
            binding.chipDate.isCloseIconVisible = false
            viewModel.updateCapacity(-1, -1)
            viewModel.updateSignalState(emptyList())
            viewModel.updateAudienceType(emptyList())
            viewModel.load()
            Log.d("dsdsdsds", "dssds")
        }

        searchView.setQuery(viewModel.query.value, false)
        viewModel.filteredList
            .onEach {
                adapter.submitList(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        if (viewModel.startCapacity.value != -1 || viewModel.endCapacity.value != -1 || viewModel.signalState.value.isNotEmpty() || viewModel.audienceType.value.isNotEmpty()) {
            binding.chipDate.isChecked = true
            binding.chipDate.isCheckable = false
            binding.chipDate.isCloseIconVisible = true
        }

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


    private fun initRcView(binding: FragmentSignalisationRegisterBinding) {


        Log.d("SIZE", childFragmentManager.fragments.toString())

        adapter = SignalisationRegisterAdapter { audienceId ->
            requireParentFragment().requireParentFragment().requireParentFragment()
                .requireParentFragment().findNavController()
                .navigate(
                    R.id.action_bottomNavigationFragment_to_audienceInfoFragment,
                    bundleOf(AudienceInfoFragment.AUDIENCE_ID to audienceId)
                )
        }
        binding.list.adapter = adapter
    }


    private fun filterList(query: String?) {
        viewModel.updateQuery(query ?: "")
        viewModel.filterList(
            query,
            viewModel.signalState.value,
            viewModel.audienceType.value,
            viewModel.startCapacity.value,
            viewModel.endCapacity.value
        )
    }

    private fun setFilters(
        binding: FragmentSignalisationRegisterBinding
    ) {
        val dialogView = layoutInflater.inflate(R.layout.filters_signalisation_dialog, null)
        val chipSignalGroup = dialogView.findViewById<ChipGroup>(R.id.signalisation_group)
        val chipAudienceGroup = dialogView.findViewById<ChipGroup>(R.id.audience_type_group)
        val startCapacity = dialogView.findViewById<EditText>(R.id.startCapacity)
        val endCapacity = dialogView.findViewById<EditText>(R.id.endCapacity)
        val builder = MaterialAlertDialogBuilder(requireContext())

        if (viewModel.startCapacity.value != -1) {
            startCapacity.text =
                Editable.Factory.getInstance().newEditable(viewModel.startCapacity.value.toString())
        }
        if (viewModel.startCapacity.value != -1) {
            endCapacity.text =
                Editable.Factory.getInstance().newEditable(viewModel.endCapacity.value.toString())
        }

        if (viewModel.signalState.value.isNotEmpty()) {
            for (i in 0 until chipSignalGroup.childCount) {
                val chip = chipSignalGroup.getChildAt(i) as Chip
                if (viewModel.signalState.value.contains(i)) {
                    chip.isChecked = true
                }
            }
        }

        if (viewModel.audienceType.value.isNotEmpty()) {
            for (i in 0 until chipAudienceGroup.childCount) {
                val chip = chipAudienceGroup.getChildAt(i) as Chip
                if (viewModel.audienceType.value.contains(i)) {
                    chip.isChecked = true
                }
            }
        }

        val dialog = builder.setView(dialogView)
            .setTitle("Выберите фильтры")
            .setPositiveButton("Применить") { dialog, which ->

            }
            .setCancelable(false)
            .setNegativeButton("Отмена") { dialog, which ->
            }


        val alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val selectedSignalStates = mutableListOf<Int>()
            for (i in 0 until chipSignalGroup.childCount) {
                val chip = chipSignalGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedSignalStates.add(i)
                }
            }
            viewModel.updateSignalState(selectedSignalStates)
            val selectedAudienceTypes = mutableListOf<Int>()
            for (i in 0 until chipAudienceGroup.childCount) {
                val chip = chipAudienceGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedAudienceTypes.add(i)
                }
            }

            if (startCapacity.text.toString() != "" && endCapacity.text.toString() != "")
                viewModel.updateCapacity(
                    startCapacity.text.toString().toInt(),
                    endCapacity.text.toString().toInt()
                )

            viewModel.updateAudienceType(selectedAudienceTypes)
            Toast.makeText(
                requireContext(),
                selectedSignalStates.toString() + selectedAudienceTypes.toString(),
                Toast.LENGTH_LONG
            )
                .show()

            viewModel.filterList(
                viewModel.query.value,
                viewModel.signalState.value,
                viewModel.audienceType.value,
                viewModel.startCapacity.value,
                viewModel.endCapacity.value
            )

            binding.chipDate.isChecked = true
            binding.chipDate.isCheckable = false
            binding.chipDate.isCloseIconVisible = true
            alertDialog.dismiss()
        };


    }


}