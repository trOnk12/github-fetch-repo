package com.example.brightinventions.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.brightinventions.ui.repositorySearch.RepositorySearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepositorySearchViewModel::class)
    internal abstract fun loginViewModel(viewModel: RepositorySearchViewModel): ViewModel

}
