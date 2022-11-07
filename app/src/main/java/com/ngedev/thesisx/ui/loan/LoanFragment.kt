package com.ngedev.thesisx.ui.loan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.databinding.FragmentBorrowBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.borrowModule
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.ui.detail.loan_detail.LoanAdapter
import com.ngedev.thesisx.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class LoanFragment : Fragment() {

    private var _binding: FragmentBorrowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoanViewModel by viewModel()
    private lateinit var adapter: LoanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(borrowModule)
        _binding = FragmentBorrowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser().observe(viewLifecycleOwner, ::getCurrentUser)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshUser().observe(viewLifecycleOwner, ::refreshResponse)
        }
    }

    private fun getCurrentUser(resource: Resource<User>) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                resource.data?.let {
                    it.borrowing?.let { loans ->
                        if (loans.isNotEmpty()) {
                            setLayoutConditionWhenEmpty(false)
                            viewModel.getAllUserThesisBorrow(loans)
                                .observe(viewLifecycleOwner, ::setBorrowing)
                        } else {
                            setLayoutConditionWhenEmpty(true)
                        }

                    }
                }


            }
            is Resource.Loading -> {
                loadingState(true)
            }
            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()

            }
        }

    }

    private fun refreshResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                binding.swipeRefresh.isRefreshing = false
            }
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                binding.swipeRefresh.isRefreshing = false
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun setLayoutConditionWhenEmpty(emptyFavorite: Boolean) {
        binding.rvThesis.isVisible = !emptyFavorite
        binding.layoutEmptyLoan.isVisible = emptyFavorite
    }

    private fun deleteResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                Snackbar.make(binding.root, "Riwayat berhasil di hapus", Snackbar.LENGTH_LONG)
                    .show()
            }
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }

            else -> {}
        }
    }

    private fun setBorrowing(resource: Resource<List<Loan>>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(state = false)
                adapter = LoanAdapter(requireContext())
                resource.data?.let {
                    Log.d("MyForm", it.toString())
                    adapter.setItems(it)
                    binding.layoutEmptyLoan.isVisible = it.isEmpty()
                }
                binding.rvThesis.adapter = adapter
                binding.rvThesis.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

                adapter.onItemClick = { loan ->
                    viewModel.deleteLoan(loan.uid)
                        .observe(viewLifecycleOwner, ::deleteResponse)
                    viewModel.changeStateBorrow(State.Thesis.NOT_BORROWING, loan.thesisId)
                        .observe(viewLifecycleOwner) {}
                }


            }

            is Resource.Loading -> {
                loadingState(state = true)
            }

            is Resource.Error -> {
                loadingState(state = false)
            }

            else -> {}
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