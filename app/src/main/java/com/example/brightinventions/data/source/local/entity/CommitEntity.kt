package com.example.brightinventions.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Commit")
data class CommitEntity(
    @ColumnInfo(name = "repositoryId") val repositoryId: Int,
    @ColumnInfo(name = "authorName") val authorName: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "sha") val SHA: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}