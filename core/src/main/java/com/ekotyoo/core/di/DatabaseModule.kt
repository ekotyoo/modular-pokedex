package com.ekotyoo.core.di

import android.content.Context
import androidx.room.Room
import com.ekotyoo.core.data.source.local.room.PokemonDao
import com.ekotyoo.core.data.source.local.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes("pokedex".toCharArray())
        val factory = SupportFactory(passPhrase)
        return Room.databaseBuilder(context, PokemonDatabase::class.java, "pokemon.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()
}