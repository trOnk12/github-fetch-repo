package com.example.brightinventions.ui.repositorySearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.brightinventions.GithubApp
import com.example.brightinventions.R
import com.example.brightinventions.core.extension.observe
import com.example.brightinventions.data.RepositoryDomainMapper
import com.example.brightinventions.databinding.RepositorySearchFragmentBinding
import com.example.brightinventions.ui.model.Repository
import com.example.brightinventions.ui.repositoryDetail.RepositoryDetailFragment.Companion.NAVIGATION_REPOSITORY_ARGUMENT_NAME

import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class RepositorySearchFragment : Fragment(R.layout.repository_search_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var repositorySearchFragmentBinding: RepositorySearchFragmentBinding
    private val repositorySearchViewModel: RepositorySearchViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RepositorySearchViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        initializeDaggerDependency()
        super.onAttach(context)
    }

    private fun initializeDaggerDependency() {
        DaggerRepositorySearchComponent
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
            RepositorySearchFragmentBinding.inflate(inflater, container, false)
        return repositorySearchFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositorySearchFragmentBinding.apply {
            lifecycleOwner = this@RepositorySearchFragment
            viewModel = repositorySearchViewModel
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        observe(repositorySearchViewModel.repository, ::onRepositoryChange)
        observe(repositorySearchViewModel.errorMessage, ::onErrorMessage)
    }

    private fun onRepositoryChange(repositoryDataState: RepositoryDataState) {
        when (repositoryDataState) {
            is RepositoryDataState.Data -> showRepositoryDetail(
                RepositoryDomainMapper.mapToRepositoryPresentation(
                    repositoryDataState.data
                )
            )
            is RepositoryDataState.EmptyData -> showEmptyFragment()
        }
    }

    private fun showRepositoryDetail(data: Repository) {
        val bundle = bundleOf(NAVIGATION_REPOSITORY_ARGUMENT_NAME to data)

        findNavController().navigate(
            R.id.action_repositorySearchFragment_to_repositoryDetailFragment,
            bundle
        )
    }

    private fun showEmptyFragment() {
        findNavController().navigate(R.id.action_repositorySearchFragment_to_emptyDataFragment)
    }

    private fun onErrorMessage(message: String) {
        Snackbar.make(repositorySearchFragmentBinding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

}

