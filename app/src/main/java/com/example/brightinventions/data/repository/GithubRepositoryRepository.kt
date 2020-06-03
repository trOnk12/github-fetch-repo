package com.example.brightinventions.data.repository

import com.example.brightinventions.data.RepositoryWithCommitNetworkMapper
import com.example.brightinventions.data.source.local.EmptyCacheException
import com.example.brightinventions.data.source.local.GithubLocalSource
import com.example.brightinventions.data.source.remote.GithubRemoteSource
import com.example.brightinventions.data.source.remote.network.NetworkManager
import com.example.brightinventions.data.source.remote.network.NetworkState
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import javax.inject.Inject

class GithubRepositoryRepository @Inject constructor(
    private val githubRemoteSource: GithubRemoteSource,
    private val networkManager: NetworkManager,
    private val githubLocalSource: GithubLocalSource
) : IGithubRepositoryRepository {

    override suspend fun getRepository(ownerName: String, repositoryName: String): Repository {
        return when (networkManager.getNetworkState()) {
            is NetworkState.Online -> get(ownerName, repositoryName)
            is NetworkState.Offline -> getCachedData(ownerName, repositoryName)
        }
    }

    private suspend fun get(ownerName: String, repositoryName: String): Repository {
        return try {
            getCachedData(ownerName, repositoryName)
        } catch (emptyCacheException: EmptyCacheException) {
            fetchRemoteSource(ownerName, repositoryName)
        }
    }

    private suspend fun getCachedData(ownerName: String, repositoryName: String) =
        githubLocalSource.getRepository(ownerName, repositoryName)

    private suspend fun fetchRemoteSource(ownerName: String, repositoryName: String) =
        githubRemoteSource.getRepositoryWithCommit(
            ownerName,
            repositoryName
        ).let { repositoryWithCommitNetwork ->
            githubLocalSource.cacheData(
                repositoryWithCommitNetwork,
                RepositorySearchCriteria(ownerName, repositoryName)
            )

            RepositoryWithCommitNetworkMapper.mapToRepository(repositoryWithCommitNetwork)
        }

}



