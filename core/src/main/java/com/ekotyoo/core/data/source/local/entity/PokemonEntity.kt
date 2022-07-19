package com.ekotyoo.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
)
