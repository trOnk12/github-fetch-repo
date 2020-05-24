package com.example.brightinventions.utils

import com.example.brightinventions.domain.usecase.RepositorySearchCriteria
import javax.inject.Inject

const val QUERY_DIVIDE_CHARACTER = '/'

class RepositorySearchQueryExtractor @Inject constructor() {

    fun extract(query: String): RepositorySearchCriteria =
        RepositorySearchCriteria(extractOwnerName(query), extractRepositoryName(query))

    private fun extractRepositoryName(query: String): String {
        var repositoryName = ""

        for (letter in query) {
            if (letter == QUERY_DIVIDE_CHARACTER) {
                repositoryName += query
            }
        }

        return repositoryName
    }

    private fun extractOwnerName(query: String): String {
        var ownerName = ""

        for (letter in query) {
            ownerName += letter
            if (letter == QUERY_DIVIDE_CHARACTER) {
                break;
            }
        }
        return ownerName
    }

}