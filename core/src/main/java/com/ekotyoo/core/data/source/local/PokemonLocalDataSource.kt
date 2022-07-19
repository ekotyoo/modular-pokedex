package com.ekotyoo.core.data.source.local

import com.ekotyoo.core.data.source.local.entity.PokemonDetailEntity
import com.ekotyoo.core.data.source.local.entity.PokemonEntity
import com.ekotyoo.core.data.source.local.room.PokemonDao
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
) {

    fun getPokemons() = pokemonDao.getAllPokemons()

    fun getFavoritePokemons() = pokemonDao.getFavoritePokemons()

    fun getPokemonDetail(name: String) = pokemonDao.getPokemonDetail(name)

    suspend fun insertPokemons(pokemons: List<PokemonEntity>) = pokemonDao.insertPokemons(pokemons)

    suspend fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity) =
        pokemonDao.insertPokemonDetail(pokemonDetailEntity)

    suspend fun updatePokemon(
        pokemonDetailEntity: PokemonDetailEntity,
        isFavorite: Boolean,
    ) {
        pokemonDao.updatePokemon(pokemonDetailEntity.name, pokemonDetailEntity, isFavorite)
    }
}