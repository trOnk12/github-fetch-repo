package com.example.brightinventions.domain.usecase

import com.example.brightinventions.core.UseCase
import com.example.brightinventions.domain.model.NetworkState
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import com.example.brightinventions.domain.repository.INetworkStateRepository

class GetRepositoryUseCase(
    private val githubRepository: IGithubRepositoryRepository,
    private val networkStateRepository: INetworkStateRepository
) : UseCase<RepositorySearchCriteria, Repository>() {

    override fun execute(params: RepositorySearchCriteria): Repository {
        return when (networkStateRepository.getNetworkState()) {
            is NetworkState.Offline -> githubRepository.getOffline(
                repositoryName = params.repositoryName,
                ownerName = params.ownerName
            )
            is NetworkState.Online -> githubRepository.get(
                repositoryName = params.repositoryName,
                ownerName = params.ownerName
            )
        }
    }

}

data class RepositorySearchCriteria(
    val repositoryName: String,
    val ownerName: String
)