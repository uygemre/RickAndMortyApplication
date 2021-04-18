package com.base.rickandmorty.ui.pages.detail.repository

import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.EpisodeNameAndAirDateResponse
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface DetailActivityContract {

    interface Repository {
        fun getEpisodeNameAndAirDate(episode_number: Int?)
        val getEpisodeNameAndAirDatePublishSubject: PublishSubject<DataFetchResult<EpisodeNameAndAirDateResponse>>

        fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO)
        fun deleteFavoritesById(favoritePersonId: Int?)

        fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable)
    }

    interface Remote {
        fun getEpisodeNameAndAirDate(episode_number: Int?): Single<EpisodeNameAndAirDateResponse>
    }

    interface Locale {
        fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO)
        fun deleteFavoritesById(favoritePersonId: Int?)
    }
}