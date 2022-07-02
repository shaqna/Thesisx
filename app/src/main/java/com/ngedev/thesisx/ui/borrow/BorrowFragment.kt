package com.ngedev.thesisx.ui.borrow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.FragmentBorrowBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.borrowModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.ui.home.ThesisAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class BorrowFragment : Fragment() {

    private var _binding: FragmentBorrowBinding? = null
    private val binding get() = _binding
    private val viewModel: BorrowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(borrowModule)
        _binding = FragmentBorrowBinding.inflate(inflater, container, false )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser().observe(viewLifecycleOwner) { user ->
            user.data?.borrowing.let { borrowingList ->

                if(borrowingList!=null) {
                    if(borrowingList.isNotEmpty()) {
                        setLayoutConditionWhenEmpty(false)
                        viewModel.getAllUserThesisBorrow(borrowingList)
                            .observe(viewLifecycleOwner, ::setBorrowing)
                    } else {
                        setLayoutConditionWhenEmpty(true)
                    }
                }



            }
        }
    }

    private fun setLayoutConditionWhenEmpty(emptyFavorite: Boolean) {
        binding?.rvThesis?.isVisible = !emptyFavorite
        binding?.layoutEmptyFavorite?.isVisible = emptyFavorite
    }

    private fun setBorrowing(resource: Resource<List<Thesis>>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(state = false)
                val adapter = ThesisAdapter(requireContext())
                resource.data?.let { theses ->
                    adapter.setItems(theses)
                    binding?.rvThesis?.adapter = adapter
                    binding?.rvThesis?.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
        binding?.progressBar?.isVisible = state
    }
}