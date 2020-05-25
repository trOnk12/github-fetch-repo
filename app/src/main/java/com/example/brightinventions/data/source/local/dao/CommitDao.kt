package com.example.brightinventions.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.brightinventions.data.source.local.entity.CommitEntity

@Dao
interface CommitDao {

    @Insert
    fun insert(commitEntity: List<CommitEntity>)

}