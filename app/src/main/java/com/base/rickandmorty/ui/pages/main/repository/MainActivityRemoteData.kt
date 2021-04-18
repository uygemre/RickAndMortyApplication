package com.base.rickandmorty.ui.pages.main.repository

import com.base.data.request.RickAndMortyApiService
import com.base.data.response.CharacterResponse
import io.reactivex.Single

class MainActivityRemoteData(private val apiService: RickAndMortyApiService) :
    MainActivityContract.Remote {

    override fun getCharacters(): Single<CharacterResponse> = apiService.getCharacters()

    override fun getCharactersWithFilters(
        name: String?,
        status: String?
    ): Single<CharacterResponse> = apiService.getCharactersWithFilters(name, status)

}
