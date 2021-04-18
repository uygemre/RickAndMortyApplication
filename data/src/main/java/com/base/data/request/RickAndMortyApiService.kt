package com.base.data.request

import com.base.data.response.CharacterResponse
import com.base.data.response.EpisodeNameAndAirDateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {

    @GET("https://rickandmortyapi.com/api/character")
    fun getCharacters(): Single<CharacterResponse>

    @GET("https://rickandmortyapi.com/api/episode/{episode_number}")
    fun getEpisodeNameAndAirDate(
            @Path("episode_number") episode_number: Int?
    ): Single<EpisodeNameAndAirDateResponse>

    @GET("https://rickandmortyapi.com/api/character/")
    fun getCharactersWithFilters(
        @Query("name") name: String?,
        @Query("status") status: String?
    ): Single<CharacterResponse>

}