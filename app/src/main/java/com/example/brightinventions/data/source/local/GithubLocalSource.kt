package com.example.brightinventions.data.source.local

import com.example.brightinventions.data.mapper.RepositoryWithCommitMapper
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

    fun getRepository(ownerName: String, repositoryName: String): Repository? {
        repositoryDao.get(ownerName, repositoryName).let {
            return if (it == null) {
                null
            } else {
                repositoryWithCommitMapper.map(it)
            }
        }
    }

    fun create(commitEntity: CommitEntity) {
        commitDao.insert(commitEntity)
    }

    fun create(repositoryEntity: RepositoryEntity) {
        repositoryDao.insert(repositoryEntity)
    }

}