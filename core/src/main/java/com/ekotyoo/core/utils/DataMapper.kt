package com.ekotyoo.core.utils

import com.ekotyoo.core.data.source.local.entity.PokemonDetailEntity
import com.ekotyoo.core.data.source.local.entity.PokemonEntity
import com.ekotyoo.core.data.source.remote.response.PokemonDetailResponse
import com.ekotyoo.core.data.source.remote.response.PokemonListItem
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.core.domain.model.Stat

object DataMapper {
    fun mapResponsesToPokemonEntities(pokemonListItemResponse: List<PokemonListItem>): List<PokemonEntity> {
        return pokemonListItemResponse.map {
            PokemonEntity(it.name, it.url)
        }
    }

    fun mapPokemonEntitiesToDomains(pokemonEntities: List<PokemonEntity>): List<Pokemon> {
        return pokemonEntities.map {
            Pokemon(it.name, it.url, it.isFavorite)
        }
    }

    fun mapPokemonDetailResponseToEntity(pokemonDetailResponse: PokemonDetailResponse): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = pokemonDetailResponse.id,
            name = pokemonDetailResponse.name,
            stats = pokemonDetailResponse.stats.map {
                PokemonDetailEntity.StatEntity(name = it.stat.name, baseStat = it.baseStat)
            },
            baseExperience = pokemonDetailResponse.baseExperience,
            weight = pokemonDetailResponse.weight,
            height = pokemonDetailResponse.height,
            sprites = pokemonDetailResponse.sprites.getData(),
        )
    }

    fun mapPokemonDetailEntityToDomain(pokemonDetailEntity: PokemonDetailEntity): PokemonDetail {
        return PokemonDetail(
            id = pokemonDetailEntity.id,
            name = pokemonDetailEntity.name,
            stats = pokemonDetailEntity.stats.map {
                Stat(name = it.name, baseStat = it.baseStat)
            },
            baseExperience = pokemonDetailEntity.baseExperience,
            weight = pokemonDetailEntity.weight,
            height = pokemonDetailEntity.height,
            sprites = pokemonDetailEntity.sprites,
            isFavorite = pokemonDetailEntity.isFavorite
        )
    }

    fun mapDomainToPokemonDetailEntity(pokemonDetail: PokemonDetail): PokemonDetailEntity {
        return PokemonDetailEntity(
            id = pokemonDetail.id,
            name = pokemonDetail.name,
            stats = pokemonDetail.stats.map { PokemonDetailEntity.StatEntity(it.name, it.baseStat) },
            baseExperience = pokemonDetail.baseExperience,
            weight = pokemonDetail.weight,
            height = pokemonDetail.height,
            sprites = pokemonDetail.sprites,
            isFavorite = pokemonDetail.isFavorite
        )
    }
}