package com.base.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.base.data.database.model.FavoriteRickAndMortyDTO
import io.reactivex.Single

@Dao
interface FavoriteRickAndMortyDAO: BaseDao<FavoriteRickAndMortyDTO> {

    @Query("DELETE FROM FavoriteRickAndMorty WHERE personId = :personId")
    fun deleteById(personId: Int)

}