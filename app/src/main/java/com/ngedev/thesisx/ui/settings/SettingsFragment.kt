package com.ngedev.thesisx.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.FragmentSettingsBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.settingsModule
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.ui.welcome.WelcomeActivity
import com.ngedev.thesisx.utils.ExtraName.EMAIL
import com.ngedev.thesisx.utils.ExtraName.USERNAME
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(settingsModule)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsernameAndEmail().observe(viewLifecycleOwner, ::setEmailAndPassword)

        binding.additionalSettings.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    AdditionalSettingsActivity::class.java
                ).apply {
                    viewModel.email.observe(viewLifecycleOwner) { email ->
                        putExtra(EMAIL, email)
                    }
                    viewModel.username.observe(viewLifecycleOwner) { username ->
                        putExtra(USERNAME, username)

                    }
                }
            )
        }

        binding.aboutLayout.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    AboutActivity::class.java
                )
            )
        }

        binding.logoutLayout.setOnClickListener {
            showDialogLogout()
        }
    }

    private fun showDialogLogout() {
        val materialBuilder = MaterialAlertDialogBuilder(requireContext()).create()
        val inflater: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null)
        val btnAccept: Button = inflater.findViewById(R.id.btn_accept)
        val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)

        btnAccept.setOnClickListener {
            materialBuilder.dismiss()
            viewModel.signOut()

            requireContext().startActivity(
                Intent(
                    requireContext(),
                    WelcomeActivity::class.java
                )
            ).also {
                activity?.finishAffinity()
            }
        }

        btnCancel.setOnClickListener {
            materialBuilder.dismiss()
        }

        materialBuilder.setView(inflater)
        materialBuilder.show()
    }

    private fun setEmailAndPassword(resource: Resource<User>) {
        when(resource) {
            is Resource.Success -> {
                loadingState(false)

                resource.data?.let { account ->
                    binding.tvUsername.text = account.username
                    binding.tvEmail.text = account.email

                    viewModel.setEmail(account.email)
                    viewModel.setUsername(account.username)
                }
            }

            is Resource.Error -> {
                loadingState(false)
            }

            is Resource.Loading -> {
                loadingState(true)
            }
        }
    }

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}