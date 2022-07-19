package com.ekotyoo.core.data.source.remote.network

import com.ekotyoo.core.data.source.remote.response.PokemonDetailResponse
import com.ekotyoo.core.data.source.remote.response.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 200): Response<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): Response<PokemonDetailResponse>
}