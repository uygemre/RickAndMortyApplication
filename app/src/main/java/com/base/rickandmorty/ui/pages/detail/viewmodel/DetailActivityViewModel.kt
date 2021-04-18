package com.base.rickandmorty.ui.pages.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.base.core.extensions.toLiveData
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.DataFetchResult
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.EpisodeNameAndAirDateResponse
import com.base.rickandmorty.ui.base.viewmodel.BaseActivityViewModel
import com.base.rickandmorty.ui.pages.detail.repository.DetailActivityRepository
import javax.inject.Inject

@ActivityScope
class DetailActivityViewModel@Inject constructor(
        private val repository: DetailActivityRepository
) : BaseActivityViewModel() {

    val getEpisodeNameAndAirDatePublishSubject:
            LiveData<DataFetchResult<EpisodeNameAndAirDateResponse>> =
            Transformations.map(repository.getEpisodeNameAndAirDatePublishSubject.toLiveData(disposables)) {
                it
            }


    fun getEpisodeAndAirDate(episode_number: Int?) {
        repository.getEpisodeNameAndAirDate(episode_number)
    }

    fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO) {
        repository.insertFav(favoriteRickAndMortyDto)
    }

    fun deleteFavoritesById(favoritePersonId: Int) {
        repository.deleteFavoritesById(favoritePersonId)
    }
}