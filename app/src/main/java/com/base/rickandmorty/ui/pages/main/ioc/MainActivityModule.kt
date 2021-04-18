@file:Suppress("unused")

package com.base.rickandmorty.ui.pages.main.ioc

import androidx.appcompat.app.AppCompatActivity
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.Scheduler
import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.rickandmorty.ioc.modules.service.RickAndMortyServiceModule
import com.base.rickandmorty.ioc.builder.FragmentBuilderModule
import com.base.rickandmorty.ioc.keys.ActivityViewModelKey
import com.base.rickandmorty.ui.pages.main.MainActivity
import com.base.rickandmorty.ui.pages.main.viewmodel.MainActivityViewModel
import com.base.rickandmorty.ui.base.activity.BaseActivityModule
import com.base.rickandmorty.ui.base.viewmodel.BaseActivityViewModel
import com.base.data.request.RickAndMortyApiService
import com.base.rickandmorty.ui.pages.main.repository.MainActivityLocaleData
import com.base.rickandmorty.ui.pages.main.repository.MainActivityRemoteData
import com.base.rickandmorty.ui.pages.main.repository.MainActivityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [BaseActivityModule::class, FragmentBuilderModule::class, RickAndMortyServiceModule::class])
abstract class MainActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindActivity(activity: MainActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ActivityViewModelKey(MainActivityViewModel::class)
    @ActivityScope
    abstract fun bindViewModel(activityViewModel: MainActivityViewModel): BaseActivityViewModel

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideMainActivityRemoteData(apiService: RickAndMortyApiService) =
            MainActivityRemoteData(apiService)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideMainActivityLocaleData(favoriteRickAndMortyDao: FavoriteRickAndMortyDAO) =
                MainActivityLocaleData(favoriteRickAndMortyDao)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideMainActivityRepository(
            remote: MainActivityRemoteData,
            locale: MainActivityLocaleData,
            scheduler: Scheduler,
            compositeDisposable: CompositeDisposable
        ) = MainActivityRepository(remote, locale, scheduler, compositeDisposable)
    }
}
