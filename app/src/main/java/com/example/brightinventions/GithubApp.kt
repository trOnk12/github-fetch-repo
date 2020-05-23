package com.example.brightinventions

import android.app.Application
import android.content.Context
import com.example.brightinventions.di.module.ContextModule
import com.example.brightinventions.di.module.component.CoreComponent

class GithubApp : Application() {

    lateinit var coreComponent: CoreComponent

    companion object {
        fun coreComponent(context: Context) =
            (context.applicationContext as GithubApp).coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDaggerCoreComponent()
    }

    private fun initDaggerCoreComponent() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }


}