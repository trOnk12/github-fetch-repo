package com.example.brightinventions.data.repository

import com.example.brightinventions.domain.model.Repository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository

class GithubRepositoryRepository(
    private val remoteSource: GithubRepositoryRemote,
    private val localSource: GithubRepositoryLocal
) : IGithubRepositoryRepository {

    override fun get(repositoryName: String, ownerName: String): Repository {
        //see if local repository exists if not get remote and cache it
    }

}