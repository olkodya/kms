package com.example.kms.fragments.register

import SignalisationRegisterAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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
import com.example.kms.fragments.shared.AudienceInfoFragment
import com.example.kms.network.api.AudienceApi
import com.example.kms.repository.reposImpl.AudienceRepositoryImpl
import com.example.kms.utils.Converter
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

        binding.on.setChipBackgroundColorResource(R.color.my_green)
        binding.off.setChipBackgroundColorResource(R.color.my_red)
        binding.none.setChipBackgroundColorResource(R.color.my_yellow)

        binding.on.setOnClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.plus(0))
            binding.on.isCheckedIconVisible = true
            binding.on.isCloseIconVisible = true
            binding.on.isCheckable = false
            binding.on.isClickable = false
        }

        binding.on.setOnCloseIconClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.minus(0))
            binding.on.isCloseIconVisible = false
            binding.on.isCheckable = true
            binding.on.isChecked = false
            binding.on.isClickable = true
        }

        binding.off.setOnClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.plus(1))
            binding.off.isCheckedIconVisible = true
            binding.off.isCloseIconVisible = true
            binding.off.isCheckable = false
            binding.off.isClickable = false

        }

        binding.off.setOnCloseIconClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.minus(1))
            binding.off.isCloseIconVisible = false
            binding.off.isCheckable = true
            binding.off.isChecked = false
            binding.off.isClickable = true

        }

        binding.none.setOnClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.plus(2))
            binding.none.isCheckedIconVisible = true
            binding.none.isCloseIconVisible = true
            binding.none.isCheckable = false
            binding.none.isClickable = false
        }

        binding.none.setOnCloseIconClickListener {
            viewModel.updateSignalState(viewModel.signalState.value.minus(2))
            binding.none.isCloseIconVisible = false
            binding.none.isCheckable = true
            binding.none.isChecked = false
            binding.none.isClickable = true
        }


        viewModel.signalState.onEach {
            viewModel.filterList(
                viewModel.query.value,
                viewModel.signalState.value,
                viewModel.audienceType.value,
                viewModel.startCapacity.value,
                viewModel.endCapacity.value
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.chipDate.setOnCloseIconClickListener {
            binding.chipDate.isCheckable = true
            binding.chipDate.isChecked = false
            binding.chipDate.isCloseIconVisible = false
            viewModel.updateCapacity(-1, -1)
            viewModel.updateSignalState(emptyList())
            viewModel.updateAudienceType(emptyList())
            viewModel.load()
            binding.chipDate.text = "Фильтры"
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
        val chipAudienceGroup = dialogView.findViewById<ChipGroup>(R.id.audience_type_group)
        val startCapacity = dialogView.findViewById<EditText>(R.id.startCapacity)
        val endCapacity = dialogView.findViewById<EditText>(R.id.endCapacity)
        val error = dialogView.findViewById<TextView>(R.id.error)
        val builder = MaterialAlertDialogBuilder(requireContext())
        if (viewModel.startCapacity.value != -1) {
            startCapacity.text =
                Editable.Factory.getInstance().newEditable(viewModel.startCapacity.value.toString())
        }
        if (viewModel.startCapacity.value != -1) {
            endCapacity.text =
                Editable.Factory.getInstance().newEditable(viewModel.endCapacity.value.toString())
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

        startCapacity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val text1: String = startCapacity.getText().toString()
                val text2: String = endCapacity.getText().toString()
                if (!text1.isEmpty() && !text2.isEmpty()) {
                    val number1 = text1.toInt()
                    val number2 = text2.toInt()
                    if (number1 > number2) {
                        error.visibility = View.VISIBLE
                    } else {
                        error.visibility = View.GONE
                    }
                }
            }
        })



        endCapacity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val text1: String = startCapacity.getText().toString()
                val text2: String = endCapacity.getText().toString()
                if (!text1.isEmpty() && !text2.isEmpty()) {
                    val number1 = text1.toInt()
                    val number2 = text2.toInt()
                    if (number1 > number2) {
                        error.visibility = View.VISIBLE
                    } else {
                        error.visibility = View.GONE
                    }
                }
            }
        })

        val alertDialog = dialog.create()
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
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
            viewModel.filterList(
                viewModel.query.value,
                viewModel.signalState.value,
                viewModel.audienceType.value,
                viewModel.startCapacity.value,
                viewModel.endCapacity.value
            )
            if (viewModel.audienceType.value.isNotEmpty() || (viewModel.endCapacity.value != -1 && viewModel.startCapacity.value != -1 && viewModel.startCapacity.value <= viewModel.endCapacity.value)) {
                binding.chipDate.isChecked = true
                binding.chipDate.isCheckable = false
                binding.chipDate.isCloseIconVisible = true
                var typesString = ""
                for (el in viewModel.audienceType.value) {
                    typesString +=
                        Converter.convertAudience(
                            Converter.intToAudienceType(el)
                        ) + " "
                }
                binding.chipDate.text =
                    "Тип аудитории: " + typesString + if (viewModel.startCapacity.value != -1) {
                        "вместимость от ${viewModel.startCapacity.value} до ${viewModel.endCapacity.value}"
                    } else {
                        ""
                    }
//                binding.chipDate.text = "Тип аудитории:  , вместимость: от ${viewModel.startCapacity.value } ло ${viewModel.endCapacity.value}"
            } else {
                binding.chipDate.isChecked = false
                binding.chipDate.isCheckable = true
                binding.chipDate.isCloseIconVisible = false
            }
            alertDialog.dismiss()

        };


    }


}