package com.example.brightinventions.ui.repositorySearch

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
import com.example.brightinventions.ui.model.Author
import com.example.brightinventions.ui.model.Commit
import com.example.brightinventions.ui.model.Detail
import com.example.brightinventions.ui.model.Repository
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class RepositorySearchFragment : Fragment() {

    @Inject
    lateinit var repositorySearchViewModel: RepositorySearchViewModel
    private lateinit var repositorySearchFragmentBinding: RepositorySearchFragmentBinding

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
        return inflateBinding(inflater, container)
    }

    private fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        repositorySearchFragmentBinding =
            RepositorySearchFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@RepositorySearchFragment
                viewModel = repositorySearchViewModel
            }

        return repositorySearchFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(repositorySearchViewModel.repositoryDataState, ::onRepositoryDataState)
        observe(repositorySearchViewModel.errorMessage, ::onErrorMessage)
    }

    private fun onRepositoryDataState(repositoryDataState: RepositoryDataState) {
        when (repositoryDataState) {
            is RepositoryDataState.HasData -> showRepositoryDetail(
                Repository(
                    id = repositoryDataState.data.id,
                    commits = repositoryDataState.data.commits.map {
                        Commit(
                            Author(name = it.author.name),
                            Detail(message = it.detail.message, SHA = it.detail.SHA)
                        )
                    })
            )
            is RepositoryDataState.NoData -> showEmptyFragment()
        }
    }

    private fun onErrorMessage(message: String) {
        Snackbar.make(repositorySearchFragmentBinding.root, message, Snackbar.LENGTH_LONG).show()
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

