package com.ekotyoo.pokedex.detail

import androidx.lifecycle.*
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonUseCase: PokemonUseCase,
) : ViewModel() {

    private val _pokemonName = MutableLiveData("")

    val pokemonDetail = _pokemonName.switchMap { name ->
        pokemonUseCase.getPokemonDetail(name).asLiveData()
    }

    fun setPokemonName(name: String) {
        _pokemonName.value = name
    }

    fun addPokemonToFavorite(pokemonDetail: PokemonDetail, isFavorite: Boolean) {
        viewModelScope.launch {
            pokemonUseCase.updatePokemonFavorite(pokemonDetail, isFavorite)
        }
    }
}