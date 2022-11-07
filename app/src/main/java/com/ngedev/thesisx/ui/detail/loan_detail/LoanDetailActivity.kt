package com.ngedev.thesisx.ui.detail.loan_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ActivityDetailFormBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.loanDetailModule
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.ui.cupboard.CupBoardActivity
import com.ngedev.thesisx.utils.DateConverter
import com.ngedev.thesisx.utils.ExtraName
import com.ngedev.thesisx.utils.State
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class LoanDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFormBinding
    private val viewModel: LoanDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(loanDetailModule)

        binding = ActivityDetailFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
    }

    private fun setObserver() {
        val loan = intent.getParcelableExtra<Loan>(ExtraName.FORM)

        if (loan != null) {
//            viewModel.getLoanById(loanId).observe(this, ::setDetailLoan)
            setDetailContent(loan)
        }
    }

//    private fun setDetailLoan(resource: Resource<Loan>?) {
//        when (resource) {
//            is Resource.Error -> {
//                loadingState(false)
//                Log.d("LoanDetail", resource.message.toString())
//                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_SHORT)
//                    .show()
//            }
//
//            is Resource.Loading -> {
//                loadingState(true)
//            }
//
//            is Resource.Success -> {
//                loadingState(false)
//                resource.data?.let {
//                    setDetailContent(it)
//                }
//            }
//        }
//    }

    private fun setDetailContent(form: Loan) {
        with(binding) {
            tvBookTitle.text = form.book_title
            tvAuthorName.text = form.author
            tvYear.text = form.year.toString()
            tvName.text = form.name
            tvNpm.text = form.npm
            tvPhoneNumber.text = form.phone
            tvDate.text = DateConverter.convertMillisToString(form.date)
        }

        binding.tvLokerNumber.setOnClickListener {
            startActivity(
                Intent(
                    this@LoanDetailActivity,
                    CupBoardActivity::class.java
                )
            )
        }

        btnArrowBackClickListener()
//        btnEndLoan(form)
    }

//    private fun btnEndLoan(form: Loan) {
//        binding.btnEndLoan.setOnClickListener {
//            showDialogEndLoan(form)
//        }
//    }

//    private fun endFormResponse() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.loadingState.collect { resource ->
//                when (resource) {
//                    is Resource.Error -> {
//                        loadingState(false)
//                        Log.d("LoanDetail", resource.message.toString())
//                        Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_SHORT)
//                            .show()
//                    }
//
//                    is Resource.Loading -> {
//                        loadingState(true)
//                        finish()
//                    }
//
//                    is Resource.Success -> {
//                        loadingState(false)
//
//                    }
//                }
//            }
//        }
//    }


    private fun btnArrowBackClickListener() {
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

//    private fun showDialogEndLoan(form: Loan) {
//        val materialBuilder = MaterialAlertDialogBuilder(this).create()
//        val inflater: View =
//            LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
//        val btnAccept: Button = inflater.findViewById(R.id.btn_accept)
//        val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)
//        val dialogTitle: TextView = inflater.findViewById(R.id.tv_dialog_title)
//        val dialogDesc: TextView = inflater.findViewById(R.id.tv_desc)
//
//        btnAccept.text = "Sudah"
//        btnCancel.text = "Belum"
//        dialogTitle.text = "Selesaikan Pinjaman"
//        dialogDesc.text = "Apakah pinjamanmu sudah selesai?"
//
//
//        btnAccept.setOnClickListener {
//            viewModel.endForm(form.uid).also {
//                endFormResponse()
//            }
//            viewModel.changeStateBorrow(State.Thesis.NOT_BORROWING, form.thesisId)
//                .observe(this) {}
//
//            materialBuilder.dismiss()
//        }
//
//        btnCancel.setOnClickListener {
//            materialBuilder.dismiss()
//        }
//
//        materialBuilder.setView(inflater)
//        materialBuilder.show()
//    }

//    private fun loadingState(state: Boolean) {
//        binding.btnEndLoan.isVisible = !state
//        binding.progressBar.isVisible = state
//    }


}