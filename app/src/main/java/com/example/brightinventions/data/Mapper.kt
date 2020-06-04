package com.example.brightinventions.data

import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.data.source.local.entity.RepositoryWithCommit
import com.example.brightinventions.data.source.remote.RepositoryWithCommitNetwork
import com.example.brightinventions.domain.model.Author
import com.example.brightinventions.domain.model.Commit
import com.example.brightinventions.domain.model.Detail
import com.example.brightinventions.domain.model.Repository


class RepositoryWithCommitNetworkMapper {
    companion object {

        fun mapToRepository(
            repositoryWithCommitNetwork: RepositoryWithCommitNetwork
        ): Repository {
            return Repository(
                id = repositoryWithCommitNetwork.repositoryNetwork.id,
                commits = repositoryWithCommitNetwork.commitsNetwork.map { commitResponse ->
                    Commit(
                        author = Author(commitResponse.author?.login ?: "No author name"),
                        detail = Detail(
                            commitResponse.commit?.message ?: "No message",
                            commitResponse.sha ?: "No SHA"
                        )
                    )
                }
            )
        }

        fun mapToCommitEntity(
            repositoryWithCommitNetwork: RepositoryWithCommitNetwork
        ): List<CommitEntity> {
            return repositoryWithCommitNetwork.commitsNetwork.map { commitNetwork ->
                CommitEntity(
                    repositoryId = repositoryWithCommitNetwork.repositoryNetwork.id,
                    authorName = commitNetwork.committer?.login ?: "No author name",
                    message = commitNetwork.commit?.message ?: "No message",
                    SHA = commitNetwork.sha ?: "No SHA"
                )
            }
        }
    }

}

class RepositoryWithCommitMapper {
    companion object {
        fun mapToRepository(repositoryWithCommit: RepositoryWithCommit): Repository {
            return Repository(
                id = repositoryWithCommit.repositoryEntity.id,
                commits = repositoryWithCommit.commits.map { CommitEntityMapper.mapToCommit(it) })
        }
    }
}

class CommitEntityMapper {
    companion object {
        fun mapToCommit(it: CommitEntity): Commit {
            return Commit(author = Author(it.authorName), detail = Detail(it.message, it.SHA))
        }
    }
}

class RepositoryDomainMapper {
    companion object {
        fun mapToRepositoryPresentation(repository: Repository): com.example.brightinventions.ui.model.Repository {
            return com.example.brightinventions.ui.model.Repository(
                id = repository.id,
                commits = repository.commits.map {
                    com.example.brightinventions.ui.model.Commit(
                        com.example.brightinventions.ui.model.Author(name = it.author.name),
                        com.example.brightinventions.ui.model.Detail(
                            message = it.detail.message,
                            SHA = it.detail.SHA
                        )
                    )
                })
        }
    }
}