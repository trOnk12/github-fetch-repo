package com.example.brightinventions.data.mapper

import com.example.brightinventions.data.source.local.entity.RepositoryWithCommit
import com.example.brightinventions.domain.model.Repository
import javax.inject.Inject

class RepositoryWithCommitMapper @Inject constructor(
    private val commitMapper: CommitMapper
) {
    fun map(input: RepositoryWithCommit): Repository {
        return Repository(
            id = input.repositoryEntity.id,
            commits = input.commits.map { commitMapper.map(it) }
        )
    }
}