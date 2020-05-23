package com.example.brightinventions.feature.search_repo

interface SearchQueryExtractor<T> {
    fun extract(query: String): T
}