package com.example.brightinventions.data.repository

import com.example.brightinventions.data.mapper.CombinedResponseMapper
import com.example.brightinventions.data.source.local.GithubLocalSource
import com.example.brightinventions.data.source.local.entity.RepositoryEntity
import com.example.brightinventions.data.source.remote.model.CommitNetwork
import com.example.brightinventions.data.source.remote.model.RepositoryNetwork
import com.example.brightinventions.data.source.remote.network.GithubService
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import javax.inject.Inject

class GithubRepositoryRepository @Inject constructor(
    private val githubService: GithubService,
    private val combinedResponseMapper: CombinedResponseMapper,
    private val githubLocalSource: GithubLocalSource
) : IGithubRepositoryRepository {

    override suspend fun get(repositoryName: String, ownerName: String): Repository {
        return try {
            getCached(repositoryName, ownerName)
        } catch (exception: EmptyOfflineDataContainerException) {
            getRemote(repositoryName, ownerName)
        }
    }

    override fun getCached(repositoryName: String, ownerName: String): Repository {
        return githubLocalSource.getRepository(repositoryName, ownerName)
    }

    private suspend fun getRemote(repositoryName: String, ownerName: String): Repository {
        githubService.getRepository(repositoryName, ownerName).let { repositoryResponse ->
            githubService.getCommits(repositoryName, ownerName).let { commitResponse ->
                cacheRepository(repositoryName, ownerName, repositoryResponse)
                cacheCommit(repositoryResponse, commitResponse)
                return map(repositoryResponse, commitResponse)
            }
        }
    }

    private fun cacheRepository(
        repositoryName: String,
        ownerName: String,
        repositoryNetwork: RepositoryNetwork
    ) {
        githubLocalSource.create(
            RepositoryEntity(
                id = repositoryNetwork.id,
                ownerName = ownerName,
                repositoryName = repositoryName
            )
        )
    }

    private fun cacheCommit(
        repositoryNetwork: RepositoryNetwork,
        commitNetwork: List<CommitNetwork>
    ) {
        githubLocalSource.create(
            combinedResponseMapper.mapToCommitEntity(
                CombinedResponse(
                    repositoryNetwork,
                    commitNetwork
                )
            )
        )
    }

    private fun map(
        repositoryNetwork: RepositoryNetwork,
        commitNetwork: List<CommitNetwork>
    ): Repository {
        return combinedResponseMapper.mapToRepository(
            CombinedResponse(
                repositoryNetwork,
                commitNetwork
            )
        )
    }

}

class EmptyOfflineDataContainerException : Exception()

data class CombinedResponse(
    val repositoryNetwork: RepositoryNetwork,
    val commitNetwork: List<CommitNetwork>
)