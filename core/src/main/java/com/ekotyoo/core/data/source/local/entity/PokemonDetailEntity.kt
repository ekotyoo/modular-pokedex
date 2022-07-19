package com.ekotyoo.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail")
data class PokemonDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val stats: List<StatEntity>,
    val baseExperience: Int,
    val weight: Int,
    val height: Int,
    val sprites: List<String>,
    var isFavorite: Boolean = false,
) {
    data class StatEntity(
        val name: String,
        val baseStat: Int,
    )
}
