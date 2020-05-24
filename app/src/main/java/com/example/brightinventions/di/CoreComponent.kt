package com.example.brightinventions.di

import androidx.lifecycle.ViewModelProvider
import com.example.brightinventions.di.module.ContextModule
import com.example.brightinventions.di.module.DataBaseModule
import com.example.brightinventions.di.module.NetworkModule
import com.example.brightinventions.di.module.RepositoryModule
import com.example.brightinventions.di.module.viewmodel.ViewModelModule
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DataBaseModule::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface CoreComponent {
    fun getRepositoryUseCase(): GetRepositoryUseCase
    fun viewModelFactory(): ViewModelProvider.Factory
}