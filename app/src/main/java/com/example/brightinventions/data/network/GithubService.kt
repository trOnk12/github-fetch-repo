package com.example.brightinventions.data.network

import retrofit2.Call
import retrofit2.http.GET

interface GithubAPI {

    @GET("/repositories")
    suspend fun get(): Call<List<NetworkGithubRepository>>

}