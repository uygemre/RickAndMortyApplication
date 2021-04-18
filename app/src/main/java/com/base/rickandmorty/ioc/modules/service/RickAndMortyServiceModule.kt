package com.base.rickandmorty.ioc.modules.service

import com.base.core.ioc.scopes.ActivityScope
import com.base.data.request.RickAndMortyApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class RickAndMortyServiceModule {

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideRickAndMortyApiService(retrofit: Retrofit): RickAndMortyApiService =
            retrofit.create(RickAndMortyApiService::class.java)

    }
}
