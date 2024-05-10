package com.example.kms.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.kms.R
import com.example.kms.databinding.FragmentAuthorizationBinding
import com.example.kms.mapper.ShiftUiModelMapper
import com.example.kms.model.Operation
import com.example.kms.network.api.ShiftApi
import com.example.kms.network.api.UserApi
import com.example.kms.network.api.WatchApi
import com.example.kms.repository.NetworkUserRepository
import com.example.kms.repository.ShiftRepositoryImpl
import com.example.kms.repository.WatchRepositoryImpl
import com.example.kms.viewmodels.authorization.AuthorizationViewModel
import com.example.kms.viewmodels.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class AuthorizationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val jsonString = " {\n" +
                "        \"operation_id\": 1,\n" +
                "        \"key\": {\n" +
                "            \"key_id\": 152,\n" +
                "            \"audience\": {\n" +
                "                \"audience_id\": 1,\n" +
                "                \"number\": 1,\n" +
                "                \"capacity\": 30,\n" +
                "                \"is_exists\": true,\n" +
                "                \"signalisation\": \"NONE\",\n" +
                "                \"permissions\": [],\n" +
                "                \"audienceType\": \"STUDY\"\n" +
                "            },\n" +
                "            \"keyState\": \"GIVEN_AWAY\",\n" +
                "            \"main\": true,\n" +
                "            \"qr\": \"{key_id = 152, auditory_id = 1}\"\n" +
                "        },\n" +
                "        \"employee\": {\n" +
                "            \"employee_id\": 1,\n" +
                "            \"first_name\": \"1\",\n" +
                "            \"second_name\": \"1\",\n" +
                "            \"middle_name\": \"1\",\n" +
                "            \"image\": null,\n" +
                "            \"employee_type\": \"SERVICE\",\n" +
                "            \"employee_status\": \"FIRED\",\n" +
                "            \"divisions\": [],\n" +
                "            \"permissions\": [\n" +
                "                {\n" +
                "                    \"permission_id\": 1,\n" +
                "                    \"name\": \"permission, b!tch\",\n" +
                "                    \"employees\": [\n" +
                "                        1\n" +
                "                    ],\n" +
                "                    \"divisions\": [\n" +
                "                        {\n" +
                "                            \"division_id\": 1,\n" +
                "                            \"name\": \"Преподаватели\",\n" +
                "                            \"employees\": [\n" +
                "                                {\n" +
                "                                    \"employee_id\": 8,\n" +
                "                                    \"first_name\": \"Romanov\",\n" +
                "                                    \"second_name\": \"Eugeniy\",\n" +
                "                                    \"middle_name\": \"Leonidovich\",\n" +
                "                                    \"image\": null,\n" +
                "                                    \"employee_type\": \"TEACHER\",\n" +
                "                                    \"employee_status\": \"WORKS\",\n" +
                "                                    \"divisions\": [\n" +
                "                                        1\n" +
                "                                    ],\n" +
                "                                    \"permissions\": [],\n" +
                "                                    \"qr\": \"{employee_id = 8, second_name = Eugeniy}\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"permissions\": [\n" +
                "                                1\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"audiences\": []\n" +
                "                }\n" +
                "            ],\n" +
                "            \"qr\": \"{employee_id = 1, second_name = 1}\"\n" +
                "        },\n" +
                "        \"shift\": {\n" +
                "            \"shift_id\": 32,\n" +
                "            \"start_date_time\": \"2024-05-09T10:28:24.265+00:00\",\n" +
                "            \"end_date_time\": null,\n" +
                "            \"watch\": {\n" +
                "                \"watch_id\": 1,\n" +
                "                \"building_number\": \"2\"\n" +
                "            },\n" +
                "            \"watchman\": {\n" +
                "                \"user_id\": 1,\n" +
                "                \"username\": \"1\",\n" +
                "                \"password\": \"10$.VbACJ.JICAEbLw..Hki0HtiEH9lh38tfYW5FGH03pBaYdsa\",\n" +
                "                \"role\": \"USER\",\n" +
                "                \"employee\": 1,\n" +
                "                \"enabled\": true,\n" +
                "                \"authorities\": [\n" +
                "                    {\n" +
                "                        \"authority\": \"ROLE_USER\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"accountNonExpired\": true,\n" +
                "                \"accountNonLocked\": true,\n" +
                "                \"credentialsNonExpired\": true\n" +
                "           }\n" +
                "        },\n" +
                "        \"give_date_time\": \"2024-05-09T11:09:26.354+00:00\",\n" +
                "        \"return_date_time\": \"2024-05-09T11:12:36.717+00:00\"\n" +
                "    }"
        val string = "[" + jsonString + ", \n" + jsonString + "]"

        var operation: List<Operation?>? = null
        try {
            operation = Json { ignoreUnknownKeys = true }.decodeFromString<List<Operation>>(string)
            Log.d("SSSSSSSS", operation.toString())
        } catch (e: SerializationException) {
            println("Ошибка десериализации: ${e.message}")
        }



        val binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        // binding.test.text = operation.toString()
        val viewModel by activityViewModels<AuthorizationViewModel> {
            viewModelFactory {
                initializer {
                    AuthorizationViewModel(
                        NetworkUserRepository(UserApi.INSTANCE)
                    )
                }
            }
        }

        val profileViewModel by activityViewModels<ProfileViewModel> {
            viewModelFactory {
                initializer {
                    ProfileViewModel(
                        ShiftRepositoryImpl(ShiftApi.INSTANCE),
                        WatchRepositoryImpl(WatchApi.INSTANCE),
                        ShiftUiModelMapper()
                    )
                }
            }
        }
        val login = binding.login.text
        val password = binding.password.text
        binding.signIn.setOnClickListener {
            viewModel.login(login.toString(), password.toString())
        }

        viewModel.state.onEach { state ->
            if (state.isSuccess) {
                findNavController().navigate(R.id.action_authorizationFragment_to_bottomNavigationFragment)
                profileViewModel.login()
            } else {

            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<AuthorizationViewModel> {
            viewModelFactory {
                initializer {
                    AuthorizationViewModel(
                        NetworkUserRepository(UserApi.INSTANCE)
                    )
                }
            }
        }
        viewModel.state.onEach { state ->
            if (state.err != null) {
                Snackbar.make(
                    view.findViewById(R.id.myCoordinatorLayout),
                    "Неверный логин или пароль",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}