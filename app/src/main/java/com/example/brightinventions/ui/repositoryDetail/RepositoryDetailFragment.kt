package com.example.brightinventions.ui.repositoryDetail

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brightinventions.R
import com.example.brightinventions.core.ui.MarginItemDecoration
import com.example.brightinventions.databinding.RepositoryDetailFragmentBinding
import com.example.brightinventions.ui.model.Commit
import com.example.brightinventions.ui.repositoryDetail.adapter.CommitsAdapter
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
            commitsRv.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.default_padding).toInt()))
            repositiory = arguments?.getParcelable(NAVIGATION_REPOSITORY_ARGUMENT_NAME)
            sendCommitsBtn.setOnClickListener { handleSendCommits(commitsAdapter.commitsToShare) }
        }
    }

    private fun handleSendCommits(commitsToSend: ArrayList<Commit>) {
        if (commitsToSend.isEmpty()) {
            showSnackBar(getString(R.string.empty_commits_to_send_info))
        } else {
            startIntentChooser(commitsToSend)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            repositoryDetailFragmentBinding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun startIntentChooser(commitsToSend: ArrayList<Commit>) {
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

