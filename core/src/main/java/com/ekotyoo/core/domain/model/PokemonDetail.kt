package com.ekotyoo.core.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val stats: List<Stat>,
    val baseExperience: Int,
    val weight: Int,
    val height: Int,
    val sprites: List<String>,
    val isFavorite: Boolean = false,
) {
    val nameFormatted: String
        get() = name.replaceFirstChar { it.uppercaseChar() }
    val imageUrl: String
        get() {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        }
}

data class Stat(
    val name: String,
    val baseStat: Int,
)
