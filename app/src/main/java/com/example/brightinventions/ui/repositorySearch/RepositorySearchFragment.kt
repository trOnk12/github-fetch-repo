package com.example.brightinventions.ui.repositorySearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.brightinventions.GithubApp
import com.example.brightinventions.R
import com.example.brightinventions.core.base.BaseFragment
import com.example.brightinventions.core.extension.observe
import com.example.brightinventions.databinding.RepositorySearchFragmentBinding
import com.example.brightinventions.ui.model.Author
import com.example.brightinventions.ui.model.Commit
import com.example.brightinventions.ui.model.Detail
import com.example.brightinventions.ui.model.Repository
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

const val NAVIGATION_REPOSITORY_ARGUMENT_NAME = "repository"

class RepositorySearchFragment : BaseFragment() {

    @Inject
    lateinit var repositorySearchViewModel: RepositorySearchViewModel
    private lateinit var repositorySearchFragmentBinding: RepositorySearchFragmentBinding

    override fun initializeDaggerDependency() {
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
        repositorySearchFragmentBinding = initializeDataBinding(inflater, container)
        return repositorySearchFragmentBinding.root
    }

    private fun initializeDataBinding(inflater: LayoutInflater, container: ViewGroup?) =
        RepositorySearchFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@RepositorySearchFragment
            viewModel = repositorySearchViewModel
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
            is RepositoryDataState.Data -> showRepositoryDetail(map(repositoryDataState.data))
            is RepositoryDataState.EmptyData -> showEmptyFragment()
        }
    }

    private fun map(data: com.example.brightinventions.domain.model.Repository) =
        Repository(
            id = data.id,
            commits = data.commits.map {
                Commit(
                    Author(name = it.author.name),
                    Detail(message = it.detail.message, SHA = it.detail.SHA)
                )
            })

    private fun onErrorMessage(message: String) {
        Log.d("TEST", message)
        Snackbar.make(repositorySearchFragmentBinding.root, message, Snackbar.LENGTH_LONG)
            .show()
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

}

