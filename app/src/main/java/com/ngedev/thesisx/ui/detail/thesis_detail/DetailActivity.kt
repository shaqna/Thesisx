package com.ngedev.thesisx.ui.detail.thesis_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityDetailBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.detailModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.ui.form.LoanFormActivity
import com.ngedev.thesisx.utils.ExtraName
import com.ngedev.thesisx.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadKoinModules(detailModule)
        setObserver()
    }

    private fun setObserver() {
        val thesisId = intent.getStringExtra(ExtraName.ID)

        if (thesisId != null) {
            viewModel.getThesisById(thesisId).observe(this@DetailActivity, ::setDetailThesis)
            viewModel.getCurrentUser().observe(this@DetailActivity) { resource ->
                setStateInCurrentUser(resource, thesisId)
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
        observeOnBtnBorrow(thesis)
    }

    private fun observeOnBtnBorrow(thesis: Thesis) {
        viewModel.setBorrow(thesis.borrowed)
        viewModel.isBorrowed.observe(this@DetailActivity) { borrowed ->
            stateBorrowed(borrowed)
            if (!borrowed) {
                binding.btnBorrow.setOnClickListener {
                    startActivity(
                        Intent(
                            this@DetailActivity,
                            LoanFormActivity::class.java
                        ).putExtra(ExtraName.THESIS, thesis)
                    )
                }
            }
        }
    }

    private fun observeOnBookmarkedIcon(thesis: Thesis) {
        viewModel.isFavorite.observe(this@DetailActivity) {
            stateFavorite(it)
            if (it) {
                binding.bookmarked.setOnClickListener {
                    viewModel.deleteFavoriteThesis(thesis.uid)
                        .observe(this@DetailActivity, ::deleteResponse)
                }
            } else {
                binding.bookmarked.setOnClickListener {
                    viewModel.addFavoriteThesis(thesis.uid)
                        .observe(this@DetailActivity, ::addResponse)
                }
            }
        }
    }

    private fun onBtnArrowBackClicked() {
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setStateInCurrentUser(resource: Resource<User>?, thesisId: String) {
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

    private fun stateBorrowed(state: Boolean) {
        if (state) {
            binding.btnBorrow.isEnabled = !state
            binding.btnBorrow.text = State.Button.UNAVAILABLE
        } else {
            binding.btnBorrow.isEnabled = !state
            binding.btnBorrow.text = State.Button.AVAILABLE
        }

    }

}