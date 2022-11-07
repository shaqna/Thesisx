package com.ngedev.thesisx.ui.cupboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.databinding.ActivityLockerBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.cupBoardModule
import com.ngedev.thesisx.domain.model.CupBoard
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class CupBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLockerBinding
    private val viewModel: CupBoardViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(cupBoardModule)

        viewModel.getKey().observe(this@CupBoardActivity, ::onKeyResponse)

        binding.layoutKeyLocker.isVisible = false
    }

    private fun onKeyResponse(resource: Resource<CupBoard>) {
        when(resource) {
            is Resource.Loading -> {
                loadingShow(true)
            }

            is Resource.Error -> {
                loadingShow(false)
                Snackbar.make(binding.root, "Key tidak ditemukan", Snackbar.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                loadingShow(false)
                binding.layoutKeyLocker.isVisible = true

                val key = resource.data?.key
                binding.tvLockerCode.text = key.toString()
            }
        }
    }

    private fun loadingShow(state: Boolean) {
        binding.progressBar.isVisible = state
    }


}