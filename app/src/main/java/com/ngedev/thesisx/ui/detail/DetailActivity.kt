package com.ngedev.thesisx.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityDetailBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.detailModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.ui.locker.LockerActivity
import com.ngedev.thesisx.utils.ExtraName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var key: String

    init {
        val number = (1000..9999).random()
        key = number.toString()
        Log.d("MyKEY", number.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loadKoinModules(detailModule)
        setObserver()

    }

    private fun setObserver() {
        val thesisId = intent.getStringExtra(ExtraName.EXTRA_ID)

        if (thesisId != null) {
            viewModel.getThesisById(thesisId).observe(this@DetailActivity, ::setDetailThesis)
            viewModel.getCurrentUser().observe(this@DetailActivity) {
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
        observeOnBtnBorrow(thesis)
    }

    private fun observeOnBtnBorrow(thesis: Thesis) {
        viewModel.setBorrow(thesis.borrowed)
        viewModel.isBorrowed.observe(this@DetailActivity) { borrowed ->
            stateBorrowed(borrowed)
            if (!borrowed) {
                binding.btnBorrow.setOnClickListener {
                    viewModel.addBorrowingThesis(thesis.uid)
                        .observe(this@DetailActivity) { resources ->
                            addBorrowedResponse(resources, thesis)
                        }
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

    private fun addBorrowedResponse(resource: Resource<Unit>, thesis: Thesis) {
        when (resource) {
            is Resource.Loading -> loadingState(true)
            is Resource.Success -> {
                loadingState(false)
                viewModel.changeStateBorrow(true, thesis.uid)
                    .observe(this@DetailActivity, ::successStateChanged)
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, "Gagal meminjam", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun successStateChanged(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Loading -> loadingState(true)
            is Resource.Success -> {
                loadingState(false)
                viewModel.updateKey(key).observe(this@DetailActivity, ::onUpdateKeyResponse)
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
            else -> {}
        }
    }

    private fun onUpdateKeyResponse(resource: Resource<Unit>) {
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
                startActivity(Intent(this@DetailActivity, LockerActivity::class.java))
            }
            else -> {}
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
        if(state) {
            binding.btnBorrow.isEnabled = !state
            binding.btnBorrow.text = "Dipinjam"
        } else {
            binding.btnBorrow.isEnabled = !state
            binding.btnBorrow.text = "Pinjam"
        }

    }


}