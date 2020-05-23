package com.example.brightinventions.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Repository")
class RepositoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "ownerName") val ownerName: String,
    @ColumnInfo(name = "repositoryName") val repositoryName: String
)