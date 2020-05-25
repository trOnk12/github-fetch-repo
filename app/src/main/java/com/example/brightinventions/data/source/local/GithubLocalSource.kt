package com.example.brightinventions.data.source.local

import com.example.brightinventions.data.mapper.RepositoryWithCommitMapper
import com.example.brightinventions.data.repository.EmptyOfflineDataContainerException
import com.example.brightinventions.data.source.local.dao.CommitDao
import com.example.brightinventions.data.source.local.dao.RepositoryDao
import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.data.source.local.entity.RepositoryEntity
import com.example.brightinventions.domain.model.Repository
import javax.inject.Inject

class GithubLocalSource @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val commitDao: CommitDao,
    private val repositoryWithCommitMapper: RepositoryWithCommitMapper
) {

    fun getRepository(ownerName: String, repositoryName: String): Repository {
        repositoryDao.get(ownerName, repositoryName).let { repositoryWithCommit ->
            return if (repositoryWithCommit == null) {
                throw EmptyOfflineDataContainerException()
            } else {
                repositoryWithCommitMapper.map(repositoryWithCommit)
            }
        }
    }

    fun create(commitEntity: List<CommitEntity>) {
        commitDao.insert(commitEntity)
    }

    fun create(repositoryEntity: RepositoryEntity) {
        repositoryDao.insert(repositoryEntity)
    }

}