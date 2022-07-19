package com.ekotyoo.core.data.source.remote

import com.ekotyoo.core.data.source.remote.network.ApiResponse
import com.ekotyoo.core.data.source.remote.network.PokemonApiService
import com.ekotyoo.core.data.source.remote.response.PokemonDetailResponse
import com.ekotyoo.core.data.source.remote.response.PokemonListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApiService: PokemonApiService,
) {

    fun getPokemons(): Flow<ApiResponse<List<PokemonListItem>>> {
        return flow {
            val response = pokemonApiService.getPokemonList()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val pokemons = body.results
                if (pokemons.isNotEmpty()) {
                    emit(ApiResponse.Success(pokemons))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                emit(ApiResponse.Error("Something went wrong."))
            }
        }.catch {
            emit(ApiResponse.Error("Something went wrong."))
        }.flowOn(Dispatchers.IO)
    }

    fun getPokemonDetail(name: String): Flow<ApiResponse<PokemonDetailResponse>> {
        return flow {
            val response = pokemonApiService.getPokemonDetail(name)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(ApiResponse.Success(body))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch {
            emit(ApiResponse.Error("Something went wrong."))
        }.flowOn(Dispatchers.IO)
    }
}