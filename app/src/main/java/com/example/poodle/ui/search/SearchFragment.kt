package com.example.poodle.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Breed
import com.example.domain.util.Result
import com.example.poodle.R
import com.example.poodle.databinding.FragmentSearchBinding
import com.example.poodle.ui.utils.toFlow
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(), SearchAdapter.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setUpRecyclerView()
        setDataStateObserver()
        observeTextChanges()
    }

    private fun setUpRecyclerView() {
        val list = viewModel.resultState.value.getResultOrNull() ?: ArrayList()
        searchAdapter =
            SearchAdapter(this, ArrayList(list))
        binding.searchResult.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResult.adapter = searchAdapter
        binding.searchResult.layoutManager?.onRestoreInstanceState(viewModel.scrollState)
    }

    private fun handleSearchResult(result: Result<List<Breed>>) {
        when (result) {
            is Result.Success -> {
                binding.progress.isVisible = false
                val breeds = result.getResultOrNull() ?: ArrayList()
                binding.noResult.isVisible = breeds.isEmpty()
                searchAdapter.updateList(breeds)
            }
            is Result.Failure -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.connection_error),
                    Toast.LENGTH_SHORT
                )
            }
            is Result.Progress -> {
                binding.progress.isVisible = true
            }
        }
    }

    override fun onClick(breed: Breed) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(breed)
        findNavController().navigate(action)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun observeTextChanges() {
        binding.search.toFlow()
            .filterNot { it.isNullOrBlank() }
            .debounce(500)
            .onEach {
                viewModel.search(it.toString())
            }
            .launchIn(lifecycleScope)
    }

    private fun setDataStateObserver() {
        lifecycleScope.launch {
            viewModel.resultState.collect {
                handleSearchResult(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.scrollState = binding.searchResult.layoutManager?.onSaveInstanceState()
        _binding = null
    }
}