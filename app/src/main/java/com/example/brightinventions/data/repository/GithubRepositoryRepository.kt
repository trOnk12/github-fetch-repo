package com.example.brightinventions.data.repository

import CommitResponse
import RepositoryResponse
import com.example.brightinventions.data.mapper.CombinedResponseMapper
import com.example.brightinventions.data.source.local.GithubLocalSource
import com.example.brightinventions.data.source.local.entity.RepositoryEntity
import com.example.brightinventions.data.source.remote.network.GithubService
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import javax.inject.Inject

class GithubRepositoryRepository @Inject constructor(
    private val githubService: GithubService,
    private val githubLocalSource: GithubLocalSource,
    private val combinedResponseMapper: CombinedResponseMapper
) : IGithubRepositoryRepository {

    override fun get(repositoryName: String, ownerName: String): Repository {
        return githubLocalSource.getRepository(repositoryName, ownerName) ?: fetchRemote(
            repositoryName,
            ownerName
        )
    }

    override fun getOffline(repositoryName: String, ownerName: String) =
        getOfflineData(repositoryName, ownerName) ?: throw EmptyOfflineDataContainerException()

    private fun getOfflineData(repositoryName: String, ownerName: String): Repository? {
        return githubLocalSource.getRepository(repositoryName, ownerName)
    }


    private fun fetchRemote(repositoryName: String, ownerName: String): Repository {
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
        repositoryResponse: RepositoryResponse
    ) {
        githubLocalSource.create(
            RepositoryEntity(
                id = repositoryResponse.id,
                ownerName = ownerName,
                repositoryName = repositoryName
            )
        )
    }

    private fun cacheCommit(
        repositoryResponse: RepositoryResponse,
        commitResponse: CommitResponse
    ) {
        githubLocalSource.create(
            combinedResponseMapper.mapToCommitEntity(
                CombinedResponse(
                    repositoryResponse,
                    commitResponse
                )
            )
        )
    }

    private fun map(repositoryResponse: RepositoryResponse, commitResponse: CommitResponse) =
        combinedResponseMapper.mapToRepository(
            CombinedResponse(
                repositoryResponse,
                commitResponse
            )
        )

}

class EmptyOfflineDataContainerException : Exception()

data class CombinedResponse(
    val repositoryResponse: RepositoryResponse,
    val commitResponse: CommitResponse
)