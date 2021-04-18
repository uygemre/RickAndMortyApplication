package com.base.rickandmorty.ui.pages.detail.repository

import com.base.data.database.dao.FavoriteRickAndMortyDAO
import com.base.data.database.model.FavoriteRickAndMortyDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivityLocaleData(
        private var favoriteRickAndMortyDao: FavoriteRickAndMortyDAO
) : DetailActivityContract.Locale {

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