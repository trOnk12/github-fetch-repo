package com.example.brightinventions.data.source.remote

import com.example.brightinventions.data.source.remote.model.CommitNetwork
import com.example.brightinventions.data.source.remote.model.RepositoryNetwork
import com.example.brightinventions.data.source.remote.network.GithubService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GithubRemoteSource @Inject constructor(
    private val githubService: GithubService
) {

    suspend fun getRepositoryWithCommit(
        ownerName: String,
        repositoryName: String
    ) = coroutineScope {
        val repositoryNetwork = async { githubService.getRepository(ownerName, repositoryName) }
        val commitsNetwork = async { githubService.getCommits(ownerName, repositoryName) }

        RepositoryWithCommitNetwork(repositoryNetwork.await(), commitsNetwork.await())
    }

}

data class RepositoryWithCommitNetwork(
    val repositoryNetwork: RepositoryNetwork,
    val commitsNetwork: List<CommitNetwork>
)