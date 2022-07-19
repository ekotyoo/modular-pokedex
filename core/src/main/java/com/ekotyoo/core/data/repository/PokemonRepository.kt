package com.ekotyoo.core.data.repository

import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.data.source.local.PokemonLocalDataSource
import com.ekotyoo.core.data.source.remote.PokemonRemoteDataSource
import com.ekotyoo.core.data.source.remote.network.ApiResponse
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.core.domain.repository.IPokemonRepository
import com.ekotyoo.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
) : IPokemonRepository {
    override fun getPokemons(): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading())
        when (val response = pokemonRemoteDataSource.getPokemons().first()) {
            is ApiResponse.Success -> {
                pokemonLocalDataSource.insertPokemons(DataMapper.mapResponsesToPokemonEntities(
                    response.data))
                val pokemons =
                    DataMapper.mapPokemonEntitiesToDomains(pokemonLocalDataSource.getPokemons()
                        .first())
                emit(Resource.Success(pokemons))
            }
            is ApiResponse.Empty -> {
                val pokemons =
                    DataMapper.mapPokemonEntitiesToDomains(pokemonLocalDataSource.getPokemons()
                        .first())
                emit(Resource.Success(pokemons))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail?>> = flow {
        emit(Resource.Loading())

        when (val response = pokemonRemoteDataSource.getPokemonDetail(name).first()) {
            is ApiResponse.Success -> {
                pokemonLocalDataSource.insertPokemonDetail(
                    DataMapper.mapPokemonDetailResponseToEntity(response.data)
                )
                emitAll(
                    pokemonLocalDataSource.getPokemonDetail(name).map {
                        Resource.Success(it?.let { pokemonDetail ->
                            DataMapper.mapPokemonDetailEntityToDomain(pokemonDetail)
                        })
                    }
                )
            }
            is ApiResponse.Empty -> {
                emitAll(
                    pokemonLocalDataSource.getPokemonDetail(name).map {
                        Resource.Success(it?.let { pokemonDetail ->
                            DataMapper.mapPokemonDetailEntityToDomain(pokemonDetail)
                        })
                    }
                )
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getFavoritePokemons(): Flow<List<Pokemon>> {
        return pokemonLocalDataSource.getFavoritePokemons().map {
            DataMapper.mapPokemonEntitiesToDomains(it)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updatePokemonFavorite(pokemonDetail: PokemonDetail, isFavorite: Boolean) {
        val pokemonDetailEntity = DataMapper.mapDomainToPokemonDetailEntity(pokemonDetail)
        pokemonLocalDataSource.updatePokemon(pokemonDetailEntity, isFavorite)
    }
}