package com.example.brightinventions.feature.search_repo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brightinventions.GithubApp
import com.example.brightinventions.R
import com.example.brightinventions.core.observe
import com.example.brightinventions.databinding.RepositorySearchFragmentBinding
import com.example.brightinventions.domain.model.Repository
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class RepositorySearchFragment : Fragment(R.layout.repository_search_fragment) {

    @Inject
    lateinit var repositorySearchViewModel: RepositorySearchViewModel
    private lateinit var repositorySearchFragmentBinding: RepositorySearchFragmentBinding

    override fun onAttach(context: Context) {
        initializeDaggerDependency()
        super.onAttach(context)
    }

    private fun initializeDaggerDependency() {
        DaggerSearchComponent
            .builder()
            .coreComponent(GithubApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repositorySearchFragmentBinding =
            RepositorySearchFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@RepositorySearchFragment
                viewModel = repositorySearchViewModel
            }
        return repositorySearchFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(repositorySearchViewModel.containerDataState, ::onRepositoryDataState)
        observe(repositorySearchViewModel.errorMessage, ::onErrorMessage)
    }

    private fun onErrorMessage(message: String) {
        Snackbar.make(repositorySearchFragmentBinding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun onRepositoryDataState(repositoryDataState: RepositoryDataState) {
        when (repositoryDataState) {
            is RepositoryDataState.HasData -> showRepositoryDetail(repositoryDataState.data)
            is RepositoryDataState.Empty -> showEmptyFragment()
        }
    }

    private fun showRepositoryDetail(data: Repository) {
        val bundle = bundleOf("repository" to data)
        findNavController().navigate(
            R.id.action_repositorySearchFragment_to_repositoryDetailFragment,
            bundle
        )
    }

    private fun showEmptyFragment() {
        findNavController().navigate(R.id.action_repositorySearchFragment_to_emptyDataFragment)
    }

}

