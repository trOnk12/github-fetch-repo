package com.example.brightinventions.domain.model

data class Commit(
    val author: Author,
    val detail: Detail
)

data class Author(
    val name: String
)

data class Detail(
    val message: String,
    val SHA: String
)