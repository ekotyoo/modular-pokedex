package com.ekotyoo.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekotyoo.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(
    pokemonUseCase: PokemonUseCase,
) : ViewModel() {
    val favoritePokemons = pokemonUseCase.getFavoritePokemons().asLiveData()
}