package com.example.brightinventions.ui.repositoryDetail.adapter

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brightinventions.R
import com.example.brightinventions.databinding.RepositoryDetailFragmentBinding
import com.example.brightinventions.ui.model.Commit
import com.google.android.material.snackbar.Snackbar

class RepositoryDetailFragment : Fragment(R.layout.repository_detail_fragment) {
    companion object {
        const val NAVIGATION_REPOSITORY_ARGUMENT_NAME = "repository"
    }

    private lateinit var repositoryDetailFragmentBinding: RepositoryDetailFragmentBinding

    private val commitsAdapter: CommitsAdapter by lazy {
        CommitsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        repositoryDetailFragmentBinding =
            RepositoryDetailFragmentBinding.inflate(inflater, container, false)
        return repositoryDetailFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositoryDetailFragmentBinding.apply {
            commitsRv.adapter = commitsAdapter
            sendCommitsBtn.setOnClickListener { handleSendCommits(commitsAdapter.commitsToShare) }
            repositiory = arguments?.getParcelable(NAVIGATION_REPOSITORY_ARGUMENT_NAME)
        }
    }

    private fun handleSendCommits(commitsToSend: ArrayList<Commit>) {
        if (commitsToSend.isEmpty()) {
            handleNoCommitSelected()
        } else {
            shareCommits(commitsToSend)
        }
    }

    private fun handleNoCommitSelected() {
        Snackbar.make(
            repositoryDetailFragmentBinding.root,
            getString(R.string.empty_commits_to_send_info),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun shareCommits(commitsToSend: ArrayList<Commit>) {
        val intent = Intent(ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, commitsToSend.toString())
        }

        val chooser = Intent.createChooser(intent, getString(R.string.chooser_title))

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(chooser)
        }
    }

}

