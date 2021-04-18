@file:Suppress("unused")

package com.base.rickandmorty.application

import android.app.Application
import android.content.Context
import com.base.component.ioc.module.RecyclerAdapterModule
import com.base.core.ioc.modules.*
import com.base.core.ioc.qualifiers.ApplicationContext
import com.base.rickandmorty.ioc.builder.ActivityBuilderModule
import com.base.rickandmorty.ioc.modules.database.RickAndMortyDatabaseModule
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        ImageModule::class,
        SystemServiceModule::class,
        TimberModule::class,
        AndroidSupportInjectionModule::class,
        RecyclerAdapterModule::class,
        ActivityBuilderModule::class,
        RickAndMortyDatabaseModule::class]
)
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindApplication(application: com.base.rickandmorty.application.Application): Application

    @Binds
    @Singleton
    @ApplicationContext
    abstract fun bindApplicationContext(application: Application): Context

}