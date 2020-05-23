package com.example.brightinventions.data.mapper

import com.example.brightinventions.data.repository.CombinedResponse
import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.domain.model.Author
import com.example.brightinventions.domain.model.Commit
import com.example.brightinventions.domain.model.Detail
import com.example.brightinventions.domain.model.Repository

class CombinedResponseMapper {

    fun mapToRepository(input: CombinedResponse): Repository {
        return Repository(
            input.repositoryResponse.id, listOf(
                Commit(
                    Author(input.commitResponse.author.login),
                    Detail(input.commitResponse.commit.message, input.commitResponse.sha)
                )
            )
        )
    }

    fun mapToCommitEntity(input: CombinedResponse): CommitEntity {
        return CommitEntity(
            id = input.commitResponse.commit.committer.id,
            repositoryId = input.repositoryResponse.id,
            authorName = input.commitResponse.committer.login,
            message = input.commitResponse.commit.message,
            SHA = input.commitResponse.sha
        )
    }

}
