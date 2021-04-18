package com.base.rickandmorty.ui.pages.main.repository

import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.data.database.model.FavoriteRickAndMortyDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityLocaleData(
        private var favoriteRickAndMortyDao: FavoriteRickAndMortyDAO
) : MainActivityContract.Locale {

    override fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO) {
        GlobalScope.launch {
            favoriteRickAndMortyDao.insert(favoriteRickAndMortyDto)
        }
    }

    override fun deleteFavoritesById(favoritePersonId: Int?) {
        GlobalScope.launch {
            favoriteRickAndMortyDao.deleteById(favoritePersonId ?: 0)
        }
    }
}