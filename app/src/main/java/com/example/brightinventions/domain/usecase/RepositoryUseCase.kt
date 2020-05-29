package com.example.brightinventions.domain.usecase

import com.example.brightinventions.core.functional.UseCase
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import javax.inject.Inject

class GetRepositoryUseCase @Inject constructor(
    private val githubRepository: IGithubRepositoryRepository
) : UseCase<Repository, RepositorySearchCriteria>() {

    override suspend fun run(params: RepositorySearchCriteria): Repository {
        return githubRepository.getRepository(
            ownerName = "octocat",
            repositoryName = "Hello-World"
        )
    }

}

data class RepositorySearchCriteria(
    val ownerName: String,
    val repositoryName: String
)