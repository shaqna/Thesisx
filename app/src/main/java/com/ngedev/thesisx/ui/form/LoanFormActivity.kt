package com.ngedev.thesisx.ui.form

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.MainActivity
import com.ngedev.thesisx.databinding.ActivityLoanThesisFormBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.loanFormModule
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.utils.DateConverter
import com.ngedev.thesisx.utils.ExtraName
import com.ngedev.thesisx.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class LoanFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanThesisFormBinding
    private val viewModel: LoanFormViewModel by viewModel()

    private var _thesis: Thesis? = null
    private val thesis get() = _thesis


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanThesisFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(loanFormModule)

        viewModel.setThesis(intent.getParcelableExtra(ExtraName.THESIS))

        setView()
        viewModel.getCurrentUser().observe(this@LoanFormActivity) {
            it.data?.let { user ->
                viewModel.setCurrentUser(user)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setView() {
        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            ivAddCover.setOnClickListener {
                startGallery()
            }
            btnUploadForm.setOnClickListener {
                if (isFormNotEmpty()) {
                    allowBorrow()
                }
            }
            tilLoanDate.editText?.inputType = InputType.TYPE_NULL
            tieLoanDate.setOnClickListener {
                setLoanDate()
            }

        }
    }

    private fun setLoanDate() {
        val dateBuilder = MaterialDatePicker.Builder.datePicker().build()
        dateBuilder.show(supportFragmentManager, dateBuilder.toString())
        dateBuilder.addOnPositiveButtonClickListener {
            binding.tilLoanDate.editText?.setText(DateConverter.convertMillisToString(it))
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            Glide.with(this).load(selectedImg).transform(CenterCrop(), RoundedCorners(8))
                .into(binding.ivAddCover)

            viewModel.setImage(selectedImg)
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Glide.with(this).load(it).transform(CenterCrop(), RoundedCorners(8))
                .into(binding.ivAddCover)
            viewModel.setImage(it)
        }

    }

    private fun pickImage() = pickImage.launch("image/*")


    private fun allowBorrow() {

        with(binding) {
            _thesis = viewModel.getThesis().value!!
            viewModel.user.observe(this@LoanFormActivity) { user ->
                val loan = Loan(
                    uid = "",
                    name = tilLoanName.editText?.text.toString(),
                    npm = tilLoanNpm.editText?.text.toString(),
                    phone = tilLoanPhoneNumber.editText?.text.toString(),
                    date = DateConverter.convertStringToMillis(tilLoanDate.editText?.text.toString()),
                    note = tilLoanNote.editText?.text.toString(),
                    photoIdentity = "",
                    book_title = thesis!!.title,
                    author = thesis!!.author,
                    year = thesis!!.year,
                    category = thesis!!.category,
                    userId = user.uid,
                    thesisId = thesis!!.uid
                )
                viewModel.uploadForm(loan, viewModel.getImageUri().value!!)
                    .observe(this@LoanFormActivity, ::uploadFormResponse)
            }
            viewModel.changeStateBorrow(State.Thesis.BORROWING, thesis!!.uid)
                .observe(this@LoanFormActivity) {}
        }
    }

    private fun uploadFormResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Loading -> {
                loadingState(true)
            }
            is Resource.Success -> {
                loadingState(false)
                startActivity(Intent(this@LoanFormActivity, MainActivity::class.java)).also {
                    finish()
                }
                Toast.makeText(
                    this,
                    "Berhasil meminjam. Cek riwayat pinjaman",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
                Log.d("Upload Error", resource.message.toString())
            }
            else -> {}
        }
    }



    private fun isFormNotEmpty(): Boolean {
        with(binding) {
            val error = "Field tidak boleh kosong"
            when {
                tilLoanName.editText?.text.toString().isEmpty() -> {
                    tilLoanName.editText?.error = error
                }
                tilLoanNpm.editText?.text.toString().isEmpty() -> {
                    tilLoanNpm.editText?.error = error
                }
                tilLoanPhoneNumber.editText?.text.toString().isEmpty() -> {
                    tilLoanPhoneNumber.editText?.error = error
                }
                tilLoanDate.editText?.text.toString().isEmpty() -> {
                    tilLoanDate.editText?.error = error

                }
                tilLoanNote.editText?.text.toString().isEmpty() -> {
                    tilLoanNote.editText?.error = error
                }
                isImageUriEmpty() -> {
                    Snackbar.make(binding.root, "Field foto kosong", Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    return true
                }
            }
            return false
        }
    }

    private fun isImageUriEmpty(): Boolean {
        viewModel.getImageUri().value.let {
            if (it != null) {
                return false
            }
        }
        return true
    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnUploadForm.isEnabled = !state
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(loanFormModule)


    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}