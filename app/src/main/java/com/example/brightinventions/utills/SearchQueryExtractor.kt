package com.example.brightinventions.utills

interface SearchQueryExtractor<T> {
    fun extract(query: String): T
}