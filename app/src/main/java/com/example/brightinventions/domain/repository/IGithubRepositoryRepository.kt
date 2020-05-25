package com.example.brightinventions.domain.repository

import com.example.brightinventions.domain.model.Repository

interface IGithubRepositoryRepository {
    suspend fun get(repositoryName: String, ownerName: String): Repository
    fun getCached(repositoryName: String, ownerName: String): Repository
}