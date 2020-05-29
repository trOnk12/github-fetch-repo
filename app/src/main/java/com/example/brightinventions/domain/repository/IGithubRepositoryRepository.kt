package com.example.brightinventions.domain.repository

import com.example.brightinventions.domain.model.Repository

interface IGithubRepositoryRepository {
    suspend fun getRepository(ownerName: String, repositoryName: String): Repository
}