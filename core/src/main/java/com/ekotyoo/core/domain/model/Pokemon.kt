package com.ekotyoo.core.domain.model

data class Pokemon(
    val name: String,
    val url: String,
    val isFavorite: Boolean = false,
) {
    val nameFormatted: String
        get() = name.replaceFirstChar { it.uppercaseChar() }
    val imageUrl: String
        get() {
            val id = url.split("/".toRegex()).dropLast(1).last()
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        }
}
