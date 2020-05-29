package com.example.brightinventions.ui.repositoryDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brightinventions.GithubApp
import com.example.brightinventions.core.base.BaseFragment
import com.example.brightinventions.databinding.RepositoryDetailFragmentBinding
import javax.inject.Inject

class RepositoryDetailFragment : BaseFragment() {

    @Inject
    lateinit var repostioryDetailViewModel: RepositoryDetailViewModel
    private lateinit var repositoryDetailFragmentBinding: RepositoryDetailFragmentBinding

    override fun initializeDaggerDependency() {
        DaggerRepositoryDetailComponent
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
        repositoryDetailFragmentBinding = initializeDataBinding(inflater, container)
        return repositoryDetailFragmentBinding.root
    }

    private fun initializeDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =
        RepositoryDetailFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@RepositoryDetailFragment
            viewModel = repostioryDetailViewModel
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {

    }

}