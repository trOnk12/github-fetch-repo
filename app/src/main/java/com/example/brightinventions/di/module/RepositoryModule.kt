package com.example.brightinventions.di.module

import com.example.brightinventions.data.repository.GithubRepositoryRepository
import com.example.brightinventions.domain.repository.IGithubRepositoryRepository
import dagger.BindsInstance
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @BindsInstance
    abstract fun provideGithubRepository(githubRepositoryRepository: GithubRepositoryRepository): IGithubRepositoryRepository

}