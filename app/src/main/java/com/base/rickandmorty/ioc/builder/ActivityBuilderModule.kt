package com.base.rickandmorty.ioc.builder

import com.base.core.ioc.scopes.ActivityScope
import com.base.rickandmorty.ui.pages.detail.DetailActivity
import com.base.rickandmorty.ui.pages.detail.ioc.DetailActivityModule
import com.base.rickandmorty.ui.pages.main.MainActivity
import com.base.rickandmorty.ui.pages.main.ioc.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    internal abstract fun bindDetailActivity(): DetailActivity
}