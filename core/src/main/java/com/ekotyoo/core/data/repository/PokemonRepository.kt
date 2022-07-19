package com.ekotyoo.core.data.repository

import com.ekotyoo.core.data.NetworkBoundResource
import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.data.source.local.PokemonLocalDataSource
import com.ekotyoo.core.data.source.remote.PokemonRemoteDataSource
import com.ekotyoo.core.data.source.remote.network.ApiResponse
import com.ekotyoo.core.data.source.remote.response.PokemonDetailResponse
import com.ekotyoo.core.data.source.remote.response.PokemonListItem
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.core.domain.repository.IPokemonRepository
import com.ekotyoo.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
) : IPokemonRepository {
    override fun getPokemons(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonListItem>>() {
            override fun loadFromDB(): Flow<List<Pokemon>> {
                return pokemonLocalDataSource.getPokemons().map {
                    DataMapper.mapPokemonEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonListItem>>> {
                return pokemonRemoteDataSource.getPokemons()
            }

            override suspend fun saveCallResult(data: List<PokemonListItem>) {
                pokemonLocalDataSource.insertPokemons(DataMapper.mapResponsesToPokemonEntities(data))
            }

        }.asFlow()

    override fun getPokemonDetail(name: String): Flow<Resource<PokemonDetail?>> {
        return object : NetworkBoundResource<PokemonDetail?, PokemonDetailResponse>() {
            override fun loadFromDB(): Flow<PokemonDetail?> {
                return pokemonLocalDataSource.getPokemonDetail(name).map {
                    if (it != null) {
                        DataMapper.mapPokemonDetailEntityToDomain(it)
                    } else {
                        null
                    }
                }
            }

            override fun shouldFetch(data: PokemonDetail?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<PokemonDetailResponse>> {
                return pokemonRemoteDataSource.getPokemonDetail(name)
            }

            override suspend fun saveCallResult(data: PokemonDetailResponse) {
                pokemonLocalDataSource
                    .insertPokemonDetail(
                        DataMapper.mapPokemonDetailResponseToEntity(data)
                    )
            }
        }.asFlow()
    }

    override fun getFavoritePokemons(): Flow<List<Pokemon>> {
        return pokemonLocalDataSource.getFavoritePokemons().map {
            DataMapper.mapPokemonEntitiesToDomains(it)
        }
    }

    override suspend fun updatePokemonFavorite(pokemonDetail: PokemonDetail, isFavorite: Boolean) {
        val pokemonDetailEntity = DataMapper.mapDomainToPokemonDetailEntity(pokemonDetail)
        pokemonLocalDataSource.updatePokemon(pokemonDetailEntity, isFavorite)
    }
}