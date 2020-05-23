package com.example.brightinventions.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.brightinventions.data.source.local.entity.RepositoryEntity
import com.example.brightinventions.data.source.local.entity.RepositoryWithCommit

@Dao
interface RepositoryDao {

    @Transaction
    @Query("SELECT * FROM Repository WHERE ownerName LIKE :ownerName AND repositoryName LIKE :repositoryName")
    fun get(ownerName: String, repositoryName: String): RepositoryWithCommit?

    @Insert
    fun insert(repositoryEntity: RepositoryEntity)

}