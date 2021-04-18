package com.base.component.ioc.module

import com.base.component.RickAndMortyRecyclerviewAdapter
import dagger.Module
import dagger.Provides

@Module
class RecyclerAdapterModule {

	@Provides
	fun provideAdapter(): RickAndMortyRecyclerviewAdapter {
		return RickAndMortyRecyclerviewAdapter()
	}
}
