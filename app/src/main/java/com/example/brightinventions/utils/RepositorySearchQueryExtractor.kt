package com.example.brightinventions.utils

import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import javax.inject.Inject

const val QUERY_DELIMITER_CHARACTER = '/'

class RepositorySearchQueryExtractor @Inject constructor() {

    fun extract(query: String): RepositorySearchCriteria =
        RepositorySearchCriteria(extractOwnerName(query), extractRepositoryName(query))

    private fun extractOwnerName(query: String) =
        query.substringBefore(QUERY_DELIMITER_CHARACTER)

    private fun extractRepositoryName(query: String) =
        query.substringAfter(QUERY_DELIMITER_CHARACTER)

}