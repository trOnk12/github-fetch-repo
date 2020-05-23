package com.example.brightinventions.di.module

import android.content.Context
import androidx.room.Room
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

}