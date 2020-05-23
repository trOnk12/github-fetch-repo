package com.example.brightinventions.data.source.remote.network

import CommitResponse
import RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/repos/{ownerName}/{repositoryName}")
    fun getRepository(@Path("ownerName") ownerName: String, @Path("repositoryName") repositoryName: String): RepositoryResponse

    @GET("/repos/{ownerName}/{repositoryName}/commits")
    fun getCommits(@Path("ownerName") ownerName: String, @Path("repositoryName") repositoryName: String): CommitResponse

}