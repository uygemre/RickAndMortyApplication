package com.base.rickandmorty.ioc.modules.database

import android.content.Context
import androidx.room.Room
import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.rickandmorty.application.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RickAndMortyDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Context): AppDatabase {
        return Room.databaseBuilder(
                app.applicationContext,
                AppDatabase::class.java, "rickandmorty.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteRickAndMortyDao(db: AppDatabase): FavoriteRickAndMortyDAO {
        return db.favoriteRickAndMortyDAO()
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}