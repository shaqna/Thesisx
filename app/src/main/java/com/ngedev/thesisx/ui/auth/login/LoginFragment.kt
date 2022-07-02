package com.ngedev.thesisx.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.MainActivity
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.FragmentLoginBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.authModule
import com.ngedev.thesisx.ui.auth.register.RegisterFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(authModule)
        with(binding) {
            btnLogin.setOnClickListener {
                val email = tilEmail.editText?.text.toString()
                val password = tilPassword.editText?.text.toString()

                viewModel.signIn(email, password).observe(viewLifecycleOwner, ::signInResponse)
            }

            tvRegister.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<RegisterFragment>(R.id.fragment_container_view)
                }
            }
        }
    }

    private fun signInResponse(resource: Resource<Unit>) {
        when (resource) {
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(),Snackbar.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                loadingState(false)
                startActivity(Intent(requireActivity(), MainActivity::class.java)).also {
                    activity?.finish()
                }
            }
        }
    }

    private fun loadingState(state: Boolean) {
        binding.apply {
            btnLogin.isEnabled = !state
            progressBar.isVisible = state
        }
    }

}