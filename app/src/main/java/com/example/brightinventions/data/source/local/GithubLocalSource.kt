package com.example.brightinventions.data.source.local

import com.example.brightinventions.data.RepositoryWithCommitMapper
import com.example.brightinventions.data.RepositoryWithCommitNetworkMapper
import com.example.brightinventions.data.source.local.dao.CommitDao
import com.example.brightinventions.data.source.local.dao.RepositoryDao
import com.example.brightinventions.data.source.local.entity.RepositoryEntity
import com.example.brightinventions.data.source.remote.RepositoryWithCommitNetwork
import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubLocalSource @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val commitDao: CommitDao
) {

    suspend fun getRepository(ownerName: String, repositoryName: String): Repository {
        repositoryDao.get(ownerName, repositoryName).let { repositoryWithCommit ->
            return if (repositoryWithCommit == null) {
                throw EmptyCacheException()
            } else {
                RepositoryWithCommitMapper.mapToRepository(repositoryWithCommit)
            }
        }
    }

    suspend fun cacheData(
        repositoryWithCommitNetwork: RepositoryWithCommitNetwork,
        repositorySearchCriteria: RepositorySearchCriteria
    ) {
        coroutineScope {
            launch {
                repositoryDao.insert(
                    RepositoryEntity(
                        id = repositoryWithCommitNetwork.repositoryNetwork.id,
                        ownerName = repositorySearchCriteria.ownerName,
                        repositoryName = repositorySearchCriteria.repositoryName
                    )
                )
            }
            launch {
                commitDao.insert(
                    RepositoryWithCommitNetworkMapper.mapToCommitEntity(
                        repositoryWithCommitNetwork
                    )
                )
            }
        }
    }

}

class EmptyCacheException : Exception()
