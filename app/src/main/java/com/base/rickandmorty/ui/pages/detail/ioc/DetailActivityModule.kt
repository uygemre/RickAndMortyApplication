package com.base.rickandmorty.ui.pages.detail.ioc

import androidx.appcompat.app.AppCompatActivity
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.Scheduler
import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.data.request.RickAndMortyApiService
import com.base.rickandmorty.ioc.builder.FragmentBuilderModule
import com.base.rickandmorty.ioc.keys.ActivityViewModelKey
import com.base.rickandmorty.ioc.modules.service.RickAndMortyServiceModule
import com.base.rickandmorty.ui.base.activity.BaseActivityModule
import com.base.rickandmorty.ui.base.viewmodel.BaseActivityViewModel
import com.base.rickandmorty.ui.pages.detail.DetailActivity
import com.base.rickandmorty.ui.pages.detail.repository.DetailActivityLocaleData
import com.base.rickandmorty.ui.pages.detail.repository.DetailActivityRemoteData
import com.base.rickandmorty.ui.pages.detail.repository.DetailActivityRepository
import com.base.rickandmorty.ui.pages.detail.viewmodel.DetailActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [BaseActivityModule::class, FragmentBuilderModule::class, RickAndMortyServiceModule::class])
abstract class DetailActivityModule {

    @Binds
    @ActivityScope
    abstract fun bindActivity(activity: DetailActivity): AppCompatActivity

    @Binds
    @IntoMap
    @ActivityViewModelKey(DetailActivityViewModel::class)
    @ActivityScope
    abstract fun bindViewModel(activityViewModel: DetailActivityViewModel): BaseActivityViewModel

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideDetailActivityRemoteData(apiService: RickAndMortyApiService) =
                DetailActivityRemoteData(apiService)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideDetailActivityLocaleData(favoriteRickAndMortyDao: FavoriteRickAndMortyDAO) =
                DetailActivityLocaleData(favoriteRickAndMortyDao)

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideDetailActivityRepository(
                remote: DetailActivityRemoteData,
                locale: DetailActivityLocaleData,
                scheduler: Scheduler,
                compositeDisposable: CompositeDisposable
        ) = DetailActivityRepository(remote, locale, scheduler, compositeDisposable)
    }
}