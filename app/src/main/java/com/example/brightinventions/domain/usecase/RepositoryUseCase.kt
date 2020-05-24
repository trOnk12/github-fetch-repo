package com.example.brightinventions.domain.usecase

import com.example.brightinventions.core.UseCase
import com.example.brightinventions.domain.model.NetworkState
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import com.example.brightinventions.domain.repository.INetworkStateRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(
    private val githubRepository: IGithubRepositoryRepository,
    private val networkStateRepository: INetworkStateRepository
) : UseCase<Repository, RepositorySearchCriteria>() {

    override suspend fun run(params: RepositorySearchCriteria): Repository {
        return githubRepository.get(
            repositoryName = "octocat",
            ownerName = "Hello-World"
        )
    }

    //        return when (networkStateRepository.getNetworkState()) {
//            is NetworkState.Offline -> githubRepository.getOffline(
//                repositoryName = params.repositoryName,
//                ownerName = params.ownerName
//            )
//            is NetworkState.Online -> githubRepository.get(
//                repositoryName = params.repositoryName,
//                ownerName = params.ownerName
//            )
//        }
}

data class RepositorySearchCriteria(
    val repositoryName: String,
    val ownerName: String
)