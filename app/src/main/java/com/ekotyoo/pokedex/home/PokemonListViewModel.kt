package com.ekotyoo.pokedex.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekotyoo.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pokemonUseCase: PokemonUseCase
) : ViewModel() {

    val pokemons = pokemonUseCase.getPokemons().asLiveData()
}