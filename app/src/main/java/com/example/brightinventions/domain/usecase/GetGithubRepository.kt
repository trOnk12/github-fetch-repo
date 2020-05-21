package com.example.brightinventions.domain.usecase

import com.example.brightinventions.core.UseCase
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository

class GetGithubRepository(
    private val githubRepository: IGithubRepositoryRepository
) : UseCase<RepositoryCriteria, Repository>() {

    override fun execute(params: RepositoryCriteria): Repository {
        return githubRepository.get(
            repositoryName = params.repositoryName,
            ownerName = params.ownerName
        )
    }

}

data class RepositoryCriteria(
    val repositoryName: String,
    val ownerName: String
)