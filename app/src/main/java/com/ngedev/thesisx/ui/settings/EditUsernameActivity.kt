package com.ngedev.thesisx.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.databinding.ActivityEditUsernameBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.settingsModule
import com.ngedev.thesisx.utils.ExtraName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class EditUsernameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUsernameBinding
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(settingsModule)
        binding = ActivityEditUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.apply {
            tilChangeUsername.editText?.setText(
                intent.getStringExtra(ExtraName.USERNAME).toString()
            )

            btnBack.setOnClickListener {
                onBackPressed()
            }


            btnSaveChange.setOnClickListener {
                viewModel.changeUsername(tilChangeUsername.editText?.text.toString())
                    .observe(this@EditUsernameActivity, ::changeUsernameResponse)
            }
        }

    }

    private fun changeUsernameResponse(resource: Resource<Unit>) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                onBackPressed()
            }
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
                Log.e("Flow Error", resource.message.toString())
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnSaveChange.isEnabled = !state
        }
    }
}