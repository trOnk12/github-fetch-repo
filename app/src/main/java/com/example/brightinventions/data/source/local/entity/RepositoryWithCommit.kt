package com.example.brightinventions.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RepositoryWithCommit(
    @Embedded val repositoryEntity: RepositoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "repositoryId"
    )
    val commits: List<CommitEntity>
)