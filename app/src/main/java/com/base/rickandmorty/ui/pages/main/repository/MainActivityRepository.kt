package com.base.rickandmorty.ui.pages.main.repository

import com.base.core.extensions.*
import com.base.core.networking.DataFetchResult
import com.base.core.networking.Scheduler
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.CharacterResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MainActivityRepository(
    private val remote: MainActivityRemoteData,
    private val locale: MainActivityLocaleData,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : MainActivityContract.Repository {

    override val getCharacterPublishSubject = PublishSubject
            .create<DataFetchResult<CharacterResponse>>()

    override fun getCharacters() {
        getCharacterPublishSubject.loading(true)
        remote.getCharacters()
                .performOnBackOutOnMain(scheduler)
                .subscribe(
                        {
                            getCharacterPublishSubject.success(it)
                        },
                        {
                            handleError(getCharacterPublishSubject, it)
                        }
                ).addTo(compositeDisposable)
    }

    override val getCharactersWithFiltersPublishSubject =
            PublishSubject.create<DataFetchResult<CharacterResponse>> ()

    override fun getCharactersWithFilters(name: String?, status: String?) {
        getCharactersWithFiltersPublishSubject.loading(true)
        remote.getCharactersWithFilters(name, status)
            .performOnBackOutOnMain(scheduler)
            .subscribe(
                {
                    getCharactersWithFiltersPublishSubject.success(it)
                },
                {
                    handleError(getCharactersWithFiltersPublishSubject, it)
                }
            ).addTo(compositeDisposable)
    }

    override fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO) {
        return locale.insertFav(favoriteRickAndMortyDto)
    }

    override fun deleteFavoritesById(favoritePersonId: Int?) {
        return locale.deleteFavoritesById(favoritePersonId)
    }

    override fun <T> handleError(
            dataFetchResult: PublishSubject<DataFetchResult<T>>,
            error: Throwable
    ) {
        dataFetchResult.failed(error)
        Timber.e(error.localizedMessage)
    }
}
