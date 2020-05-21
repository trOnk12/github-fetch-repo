package com.example.brightinventions.domain.model

data class Repository(
    val id: Int,
    val commits: List<Commit>
)
