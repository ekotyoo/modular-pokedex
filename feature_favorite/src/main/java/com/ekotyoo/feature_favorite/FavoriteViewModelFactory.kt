package com.ekotyoo.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekotyoo.core.domain.usecase.PokemonUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(pokemonUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}