package com.example.brightinventions.ui.repositoryDetail

import com.example.brightinventions.di.CoreComponent
import com.example.brightinventions.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(dependencies = [CoreComponent::class])
interface RepositoryDetailComponent {
    fun inject(repositoryDetailFragment: RepositoryDetailFragment)
}