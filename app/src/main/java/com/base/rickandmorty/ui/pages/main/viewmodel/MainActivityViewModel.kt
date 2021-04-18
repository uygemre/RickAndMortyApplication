package com.base.rickandmorty.ui.pages.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.base.component.ui.characteritem.CharacterItemDTO
import com.base.component.ui.characteritemforgrid.CharacterItemForGridDTO
import com.base.core.extensions.toLiveData
import com.base.core.ioc.scopes.ActivityScope
import com.base.core.networking.DataFetchResult
import com.base.core.ui.recyclerview.DisplayItem
import com.base.data.database.model.FavoriteRickAndMortyDTO
import com.base.data.response.CharacterResponse
import com.base.rickandmorty.ui.base.viewmodel.BaseActivityViewModel
import com.base.rickandmorty.ui.pages.main.repository.MainActivityRepository
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@ActivityScope
class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepository
) : BaseActivityViewModel() {

    val getCharacterPublish = PublishSubject.create<List<DisplayItem>>()

    val getCharacterPublishSubject: LiveData<DataFetchResult<CharacterResponse>> =
        Transformations.map(repository.getCharacterPublishSubject.toLiveData(disposables)) {
            it
        }

    val getCharactersWithFiltersPublishSubject: LiveData<DataFetchResult<CharacterResponse>> =
        Transformations.map(
            repository.getCharactersWithFiltersPublishSubject.toLiveData
                (disposables)
        ) {
            it
        }

    fun consumeCharacter(response: CharacterResponse, grid: Boolean) {
        val list = arrayListOf<DisplayItem>()
        if (!grid) {
            response.results.map {
                list.add(
                    CharacterItemDTO(
                        results = it
                    )
                )
            }
        } else {
            response.results.map {
                list.add(
                    CharacterItemForGridDTO(
                        results = it
                    )
                )
            }
        }

        getCharacterPublish.onNext(list)
    }

    fun getCharacter() {
        repository.getCharacters()
    }

    fun getCharactersWithFilters(name: String?, status: String?) {
        repository.getCharactersWithFilters(name, status)
    }

    fun insertFav(favoriteRickAndMortyDto: FavoriteRickAndMortyDTO) {
        repository.insertFav(favoriteRickAndMortyDto)
    }

    fun deleteFavoritesById(favoritePersonId: Int) {
        repository.deleteFavoritesById(favoritePersonId)
    }
}