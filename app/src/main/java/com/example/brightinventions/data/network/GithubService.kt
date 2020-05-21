package com.example.brightinventions.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GithubService {

    interface GithubRepositoryApi {

        @GET("/repos")
        suspend fun get(repositoryName: String, ownerName: String): RepositoryResponse

        @GET("/search/repositories")
        fun search(@Query("q") repositoryName: String): Call<NetworkGithubRepositorySearchResponse>

        @GET
        fun fetchLink(@Url link: String): Call<List<NetworkGithubRepository>>

    }

}