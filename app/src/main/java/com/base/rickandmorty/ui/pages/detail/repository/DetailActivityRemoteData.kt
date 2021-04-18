package com.base.rickandmorty.ui.pages.detail.repository

import com.base.data.request.RickAndMortyApiService

class DetailActivityRemoteData(
        private val apiService: RickAndMortyApiService
) : DetailActivityContract.Remote {

    override fun getEpisodeNameAndAirDate(episode_number: Int?) = apiService
            .getEpisodeNameAndAirDate(episode_number)

}