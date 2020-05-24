package com.example.brightinventions.ui.repositorySearch

import com.example.brightinventions.di.CoreComponent
import com.example.brightinventions.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class])
interface RepositorySearchComponent {
    fun inject(repositorySearchFragment: RepositorySearchFragment)
}