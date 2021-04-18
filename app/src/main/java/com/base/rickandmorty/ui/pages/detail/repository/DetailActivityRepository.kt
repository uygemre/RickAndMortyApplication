package com.base.rickandmorty.ui.pages.detail.repository

import com.base.core.extensions.*
import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.EpisodeNameAndAirDateResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class DetailActivityRepository(
        private val remote: DetailActivityRemoteData,
        private val locale: DetailActivityLocaleData,
        private val scheduler: Scheduler,
        private val compositeDisposable: CompositeDisposable
) : DetailActivityContract.Repository {

    override val getEpisodeNameAndAirDatePublishSubject =
            PublishSubject.create<DataFetchResult<EpisodeNameAndAirDateResponse>>()

    override fun getEpisodeNameAndAirDate(episode_number: Int?) {
        getEpisodeNameAndAirDatePublishSubject.loading(true)
        remote.getEpisodeNameAndAirDate(episode_number)
                .performOnBackOutOnMain(scheduler)
                .subscribe(
                        {
                            getEpisodeNameAndAirDatePublishSubject.success(it)
                        },
                        {
                            handleError(getEpisodeNameAndAirDatePublishSubject, it)
                        }
                ).addTo(compositeDisposable)
    }

    override fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO) {
        return locale.insertFav(favoriteRickAndMortyDto)
    }

    override fun deleteFavoritesById(favoritePersonId: Int?) {
        return locale.deleteFavoritesById(favoritePersonId)
    }

    override fun <T> handleError(dataFetchResult: PublishSubject<DataFetchResult<T>>, error: Throwable) {
        dataFetchResult.failed(error)
        Timber.e(error.localizedMessage)
    }
}