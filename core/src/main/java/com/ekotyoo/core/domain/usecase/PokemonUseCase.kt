package com.ekotyoo.core.domain.usecase

import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getPokemons(): Flow<Resource<List<Pokemon>>>
    fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail?>>
    fun getFavoritePokemons(): Flow<List<Pokemon>>
    suspend fun updatePokemonFavorite(pokemonDetail: PokemonDetail, isFavorite: Boolean)
}