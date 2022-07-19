package com.ekotyoo.core.domain.usecase

import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val pokemonRepository: IPokemonRepository) :
    PokemonUseCase {

    override fun getPokemons(): Flow<Resource<List<Pokemon>>> {
        return pokemonRepository.getPokemons()
    }

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail?>> {
        return pokemonRepository.getPokemonDetail(name)
    }

    override fun getFavoritePokemons(): Flow<List<Pokemon>> {
        return pokemonRepository.getFavoritePokemons()
    }

    override suspend fun updatePokemonFavorite(pokemonDetail: PokemonDetail, isFavorite: Boolean) {
        pokemonRepository.updatePokemonFavorite(pokemonDetail, isFavorite)
    }
}