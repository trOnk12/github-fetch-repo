package com.example.brightinventions.di.module

import com.example.brightinventions.data.repository.GithubRepositoryRepository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideGithubRepository(githubRepositoryRepository: GithubRepositoryRepository): IGithubRepositoryRepository

}