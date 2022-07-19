package com.ekotyoo.core.data.source.local.room

import androidx.room.*
import com.ekotyoo.core.data.source.local.entity.PokemonDetailEntity
import com.ekotyoo.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemons ORDER BY RANDOM() LIMIT 1")
    fun getRandomPokemon(): Flow<PokemonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Transaction
    suspend fun updatePokemon(
        name: String,
        pokemonDetailEntity: PokemonDetailEntity,
        isFavorite: Boolean,
    ) {
        updatePokemonDetail(pokemonDetailEntity.apply { this.isFavorite = isFavorite })
        updatePokemon(name, isFavorite)
    }

    @Query("UPDATE pokemons SET is_favorite = :isFavorite WHERE name = :name")
    suspend fun updatePokemon(name: String, isFavorite: Boolean)

    @Query("SELECT * FROM pokemons WHERE is_favorite = 1")
    fun getFavoritePokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon_detail WHERE name = :name")
    fun getPokemonDetail(name: String): Flow<PokemonDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonDetail(pokemonDetailEntity: PokemonDetailEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePokemonDetail(pokemonDetailEntity: PokemonDetailEntity)
}