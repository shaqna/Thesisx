package com.ngedev.thesisx.ui.loanDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityLoanDetailBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.loanDetailModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.ui.welcome.WelcomeActivity
import com.ngedev.thesisx.utils.ExtraName
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanDetailBinding
    private val viewModel: LoanDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoanDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadKoinModules(loanDetailModule)
        setObserver()
    }

    private fun setObserver() {
        val thesisId = intent.getStringExtra(ExtraName.EXTRA_ID)

        if (thesisId != null) {
            viewModel.getThesisById(thesisId).observe(this@LoanDetailActivity, ::setDetailThesis)
            viewModel.getCurrentUser().observe(this@LoanDetailActivity) {
                setStateInCurrentUser(it, thesisId)
            }
        }
    }


    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    private fun setDetailContent(thesis: Thesis) {

        with(binding) {
            tvAuthorName.text = thesis.author
            tvYear.text = thesis.year.toString()
            tvTitle.text = thesis.title
            tvCategory.text = thesis.category
            tvAbstract.text = thesis.thesisAbstract
        }

        onBtnArrowBackClicked()
        observeOnBookmarkedIcon(thesis)
        observeOnBtnComplete(thesis)
    }

    private fun observeOnBtnComplete(thesis: Thesis) {
        binding.btnLoanComplete.setOnClickListener {
            showDialogCompleteLoan(thesis)
        }
    }

    private fun loanComleteResponse(resource: Resource<Unit>, thesis: Thesis) {
        when (resource) {
            is Resource.Loading -> loadingState(true)
            is Resource.Success -> {
                loadingState(false)
                viewModel.changeStateBorrow(false, thesis.uid)
                    .observe(this@LoanDetailActivity, ::successStateChanged)
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, "Gagal", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun successStateChanged(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Loading -> loadingState(true)
            is Resource.Success -> {
                loadingState(false)
                finish()
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
            else -> {}
        }
    }

    private fun observeOnBookmarkedIcon(thesis: Thesis) {
        viewModel.isFavorite.observe(this@LoanDetailActivity) {
            stateFavorite(it)
            if (it) {
                binding.bookmarked.setOnClickListener {
                    viewModel.deleteFavoriteThesis(thesis.uid)
                        .observe(this@LoanDetailActivity, ::deleteResponse)
                }
            } else {
                binding.bookmarked.setOnClickListener {
                    viewModel.addFavoriteThesis(thesis.uid)
                        .observe(this@LoanDetailActivity, ::addResponse)
                }
            }
        }
    }

    private fun onBtnArrowBackClicked() {
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setStateInCurrentUser(resource: Resource<UserAccount>?, thesisId: String) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                resource.data?.let { user ->
                    viewModel.setFavorite(user.favorite.contains(thesisId))
                }
            }

            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
            else -> {}
        }

    }

    private fun setDetailThesis(resource: Resource<Thesis>) {
        when (resource) {
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }

            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Success -> {
                loadingState(false)
                resource.data?.let {
                    setDetailContent(it)
                }
            }
        }
    }


    private fun addResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }

            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Success -> {
                loadingState(false)
                Snackbar.make(
                    binding.root,
                    "Ditambahkan ke Koleksi",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    private fun deleteResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }

            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Success -> {
                loadingState(false)
                Snackbar.make(
                    binding.root,
                    "Dihapus dari Koleksi",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    private fun stateFavorite(state: Boolean) {
        if (state) {
            binding.bookmarked.setImageResource(R.drawable.bookmark_added)
        } else {
            binding.bookmarked.setImageResource(R.drawable.bookmark_not_added)
        }
    }

    private fun showDialogCompleteLoan(thesis: Thesis) {
        val materialBuilder = MaterialAlertDialogBuilder(this@LoanDetailActivity).create()
        val inflater: View =
            LayoutInflater.from(this@LoanDetailActivity).inflate(R.layout.dialog_layout, null)
        val btnAccept: Button = inflater.findViewById(R.id.btn_accept)
        val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)
        val tvDialogTitle: TextView = inflater.findViewById(R.id.tv_dialog_title)
        val tvDialogDescription: TextView = inflater.findViewById(R.id.tv_desc)

        tvDialogTitle.text = getString(R.string.loan_dialog_title)
        tvDialogDescription.text = getString(R.string.loan_description)
        btnAccept.text = "Sudah"
        btnCancel.text = "Belum"


        btnAccept.setOnClickListener {
            materialBuilder.dismiss()
            viewModel.loanComplete(thesis.uid).observe(this@LoanDetailActivity) { resource ->
                loanComleteResponse(resource, thesis)
            }
        }

        btnCancel.setOnClickListener {
            materialBuilder.dismiss()
        }

        materialBuilder.setView(inflater)
        materialBuilder.show()
    }
}