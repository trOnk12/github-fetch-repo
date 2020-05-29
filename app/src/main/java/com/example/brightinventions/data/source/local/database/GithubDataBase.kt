package com.example.brightinventions.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brightinventions.data.source.local.dao.CommitDao
import com.example.brightinventions.data.source.local.dao.RepositoryDao
import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.data.source.local.entity.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class, CommitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDataBase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun commitDao(): CommitDao
}