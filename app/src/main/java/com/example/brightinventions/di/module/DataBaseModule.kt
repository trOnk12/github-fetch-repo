package com.example.brightinventions.di.module

import android.content.Context
import androidx.room.Room
import com.example.brightinventions.data.source.local.dao.CommitDao
import com.example.brightinventions.data.source.local.dao.RepositoryDao
import com.example.brightinventions.data.source.local.database.GithubDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideGithubDatabase(context: Context): GithubDataBase {
        return Room.databaseBuilder(context, GithubDataBase::class.java, "github-database").build()
    }


    @Provides
    @Singleton
    fun provideRepositoryDao(githubDataBase: GithubDataBase): RepositoryDao {
        return githubDataBase.repositoryDao()
    }

    @Provides
    @Singleton
    fun provideCommitDao(githubDataBase: GithubDataBase): CommitDao {
        return githubDataBase.commitDao()
    }

}