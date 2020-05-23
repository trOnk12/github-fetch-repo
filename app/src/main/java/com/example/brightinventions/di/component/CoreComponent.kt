package com.example.brightinventions.di.module.component

import androidx.lifecycle.ViewModelProvider
import com.example.brightinventions.di.module.DataBaseModule
import com.example.brightinventions.di.module.NetworkModule
import com.example.brightinventions.di.module.RepositoryModule
import com.example.brightinventions.domain.usecase.GetRepositoryUseCase
import dagger.Component

@Component(modules = [DataBaseModule::class, NetworkModule::class, RepositoryModule::class])
interface CoreComponent {
    fun getRepositoryUseCase(): GetRepositoryUseCase
    fun viewModelFactory(): ViewModelProvider.Factory
}