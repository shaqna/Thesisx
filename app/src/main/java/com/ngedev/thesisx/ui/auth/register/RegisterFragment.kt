package com.ngedev.thesisx.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.R
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.databinding.FragmentRegisterBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.authModule
import com.ngedev.thesisx.ui.auth.login.LoginFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(authModule)
        with(binding) {
            activity.apply {
                tvLogin.setOnClickListener {
                    val fragmentManager = parentFragmentManager
                    fragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<LoginFragment>(R.id.fragment_container_view)
                    }
                }
                btnRegister.setOnClickListener {
                    val username = tilName.editText?.text.toString()
                    val email = tilEmail.editText?.text.toString()
                    val password = tilPassword.editText?.text.toString()

                    registerViewModel.signUp(email, password, generateUserModel(email, username))
                        .observe(viewLifecycleOwner, ::signUpResponse)

                }
            }
        }

    }

    private fun generateUserModel(email: String, username: String): UserAccount {
        return UserAccount(
            uid = "",
            username,
            email
        )
    }

    private fun signUpResponse(response: Resource<Unit>) {
        when (response) {
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Success -> {
                loadingState(false)
//                startActivity(Intent(requireActivity(), MainActivity::class.java)).also {
//                    activity?.finish()
//                }
                val fragmentManager = parentFragmentManager
                fragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<LoginFragment>(R.id.fragment_container_view)
                }
            }

            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnRegister.isEnabled = !state
        }
    }

}