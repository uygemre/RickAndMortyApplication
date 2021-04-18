package com.base.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteRickAndMorty")
data class FavoriteRickAndMortyDTO(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var personId: Int?
)