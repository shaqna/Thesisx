package com.ngedev.thesisx.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.databinding.ActivityResetPasswordBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.authModule
import com.ngedev.thesisx.utils.ExtraName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(authModule)

        setView()
    }

    private fun setView() {
        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            tilResetPassword.editText?.setText(intent.getStringExtra(ExtraName.EMAIL))

            btnResetPassword.setOnClickListener {
                viewModel.resetPassword(tilResetPassword.editText?.text.toString())
                    .observe(this@ResetPasswordActivity, ::resetPasswordResponse)
            }
        }

    }

    private fun resetPasswordResponse(resource: Resource<Unit>) {
        when (resource) {
            is Resource.Loading -> {
                Snackbar.make(
                    binding.root,
                    "Reset Password telah dikirimkan ke email milikmu",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is Resource.Success -> {
                loadingState(false)

            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnResetPassword.isEnabled = !state
        }
    }
}