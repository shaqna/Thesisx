package com.ngedev.thesisx.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngedev.thesisx.databinding.FragmentSearchBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.searchModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.ui.home.ThesisAdapter
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var thesisAdapter: ThesisAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(searchModule)
        setIntroSearchLayoutVisibility(true)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.searchThesisByTitle(newText)
                        .observe(viewLifecycleOwner, ::setListSearch)
                }
                return true
            }

        })

    }

    private fun setIntroSearchLayoutVisibility(visibility: Boolean) {
        binding.layoutIntroSearch.isVisible = visibility
    }


    private fun setListSearch(resource: Resource<List<Thesis>>?) {
        when (resource) {
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
            }

            is Resource.Success -> {
                loadingState(false)
                thesisAdapter = ThesisAdapter(requireContext())
                resource.data?.let {

                    if (it.isEmpty()) {
                        isEmptyResult(true)
                        setCountResult(it.size)
                    } else {
                        isEmptyResult(false)
                        setIntroSearchLayoutVisibility(false)
                        setCountResult(it.size)
                        thesisAdapter.setItems(it)
                        binding.rvSearchList.adapter = thesisAdapter
                        binding.rvSearchList.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }

                }
            }

            else -> {}
        }
    }

    private fun setCountResult(size: Int) {
        val resultCountText = "Hasil Pencarian ($size)"
        binding.tvResultCount.text = resultCountText
    }

    private fun isEmptyResult(emptyState: Boolean) {
        binding.layoutNotFound.isVisible = emptyState
        binding.rvSearchList.isVisible = !emptyState
    }

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}