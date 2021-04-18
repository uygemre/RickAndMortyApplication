package com.base.rickandmorty.ioc.modules.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.data.database.model.FavoriteRickAndMortyDTO

@Database(entities = [FavoriteRickAndMortyDTO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

	abstract fun favoriteRickAndMortyDAO(): FavoriteRickAndMortyDAO

}
