package com.example.brightinventions.di.module

import com.example.brightinventions.data.repository.GithubRepositoryRepository
import com.example.brightinventions.data.repository.NetworkStateRepository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import com.example.brightinventions.domain.repository.INetworkStateRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideGithubRepository(githubRepositoryRepository: GithubRepositoryRepository): IGithubRepositoryRepository

    @Singleton
    @Binds
    abstract fun provideNetworkGithubRepository(networkGithubRepository: NetworkStateRepository): INetworkStateRepository

}