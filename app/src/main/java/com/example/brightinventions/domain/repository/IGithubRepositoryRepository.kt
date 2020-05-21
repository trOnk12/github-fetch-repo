package com.example.brightinventions.domain.repository

import com.example.brightinventions.domain.model.Repository

interface IGithubRepositoryRepository {
    fun get(name: String, owner: String): Repository
}