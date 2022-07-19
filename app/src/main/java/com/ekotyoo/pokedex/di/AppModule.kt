package com.ekotyoo.pokedex.di

import com.ekotyoo.core.domain.usecase.PokemonInteractor
import com.ekotyoo.core.domain.usecase.PokemonUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun providePokemonUseCase(pokemonInteractor: PokemonInteractor): PokemonUseCase
}