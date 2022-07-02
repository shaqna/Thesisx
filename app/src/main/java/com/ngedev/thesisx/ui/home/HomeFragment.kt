package com.ngedev.thesisx.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ngedev.thesisx.databinding.FragmentHomeBinding
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.di.homeModule
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.utils.Category
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var thesisAdapter: ThesisAdapter

    private val listCategories = arrayListOf(
        Category.ALL,
        Category.CONTROL,
        Category.TELKOM_MEDIA,
        Category.ELECTRONICA,
        Category.POWER
    )

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(listCategories).apply {
            setOnItemCallback { category ->
                viewModel.getThesisByCategory(category).observe(viewLifecycleOwner) { resource ->
                    setTheses(resource)
                }
                Log.d("Category", category)
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(homeModule)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setCategory(Category.ALL)

        viewModel.category.observe(viewLifecycleOwner) {
            viewModel.getThesisByCategory(it).observe(viewLifecycleOwner) { resource ->
                setTheses(resource)
            }
        }

        with(binding) {
            rvHome.setHasFixedSize(true)
            rvHome.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            rvHome.adapter = categoryAdapter
        }

    }

    private fun setTheses(resource: Resource<List<Thesis>>?) {
        when (resource) {

            is Resource.Success -> {
                loadingState(false)
                thesisAdapter = ThesisAdapter(requireContext())
                resource.data?.let {
                    val sortedList = ArrayList<Thesis>()
                    sortedList.addAll(it)
                    sortedList.sortByDescending { thesis: Thesis ->
                        thesis.year
                    }
                    thesisAdapter.setItems(sortedList)
                }

                binding.rvThesis.adapter = thesisAdapter
                binding.rvThesis.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}