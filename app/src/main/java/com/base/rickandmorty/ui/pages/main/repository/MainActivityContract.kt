package com.base.rickandmorty.ui.pages.main.repository

import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.CharacterResponse
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface MainActivityContract {

    interface Repository {
        fun getCharacters()
        val getCharacterPublishSubject: PublishSubject<DataFetchResult<CharacterResponse>>

        val getCharactersWithFiltersPublishSubject: PublishSubject<DataFetchResult<CharacterResponse>>
        fun getCharactersWithFilters(name: String?, status: String?)

        fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO)
        fun deleteFavoritesById(favoritePersonId: Int?)

        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
    }

    interface Remote {
        fun getCharacters(): Single<CharacterResponse>
        fun getCharactersWithFilters(name: String?, status: String?): Single<CharacterResponse>
    }

    interface Locale {
        fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO)
        fun deleteFavoritesById(favoritePersonId: Int?)
    }
}
