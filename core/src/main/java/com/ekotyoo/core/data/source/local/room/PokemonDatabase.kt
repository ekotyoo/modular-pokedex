package com.ekotyoo.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ekotyoo.core.data.source.local.entity.PokemonDetailEntity
import com.ekotyoo.core.data.source.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 2)
@TypeConverters(TypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}