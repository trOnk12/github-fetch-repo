package com.example.brightinventions.data.source.remote.network

import com.example.brightinventions.data.source.remote.model.CommitNetwork
import com.example.brightinventions.data.source.remote.model.CommitResponse
import com.example.brightinventions.data.source.remote.model.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("/repos/{ownerName}/{repositoryName}")
    suspend fun getRepository(@Path("ownerName") ownerName: String, @Path("repositoryName") repositoryName: String): RepositoryResponse

    @GET("/repos/{ownerName}/{repositoryName}/commits")
    suspend fun getCommits(@Path("ownerName") ownerName: String, @Path("repositoryName") repositoryName: String): CommitResponse

}