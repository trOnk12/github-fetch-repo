package com.example.brightinventions.feature.search_repo

import com.example.brightinventions.domain.usecase.RepositorySearchCriteria

const val QUERY_DIVIDE_CHARACTER = '/'

class RepositorySearchQueryExtractor : SearchQueryExtractor<RepositorySearchCriteria> {

    override fun extract(query: String): RepositorySearchCriteria =
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