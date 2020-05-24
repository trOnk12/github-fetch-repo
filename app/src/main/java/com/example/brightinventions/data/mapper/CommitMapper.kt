package com.example.brightinventions.data.mapper

import com.example.brightinventions.data.source.local.entity.CommitEntity
import com.example.brightinventions.domain.model.Author
import com.example.brightinventions.domain.model.Commit
import com.example.brightinventions.domain.model.Detail
import javax.inject.Inject

class CommitMapper @Inject constructor(){
    fun map(input: CommitEntity): Commit {
        return Commit(
            Author(name = input.authorName),
            Detail(message = input.message, SHA = input.SHA)
        )
    }
}