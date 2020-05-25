package com.example.brightinventions.data.mapper

import com.example.brightinventions.data.repository.CombinedResponse
import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.domain.model.Author
import com.example.brightinventions.domain.model.Commit
import com.example.brightinventions.domain.model.Detail
import com.example.brightinventions.domain.model.Repository
import javax.inject.Inject

class CombinedResponseMapper @Inject constructor() {

    fun mapToRepository(input: CombinedResponse): Repository {
        return Repository(
            input.repositoryNetwork.id, commits = input.commitNetwork.map { commitResponse ->
                Commit(
                    Author(commitResponse.author.login),
                    Detail(commitResponse.commit.message, commitResponse.sha)
                )
            }
        )
    }

    fun mapToCommitEntity(input: CombinedResponse): List<CommitEntity> {
        return input.commitNetwork.map { commitResponse ->
            CommitEntity(
                id = commitResponse.commit.committer.id,
                repositoryId = input.repositoryNetwork.id,
                authorName = commitResponse.committer.login,
                message = commitResponse.commit.message,
                SHA = commitResponse.sha
            )
        }
    }

}
