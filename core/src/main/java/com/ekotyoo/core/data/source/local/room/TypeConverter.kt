package com.ekotyoo.core.data.source.local.room

import androidx.room.TypeConverter
import com.ekotyoo.core.data.source.local.entity.PokemonDetailEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromStatEntity(statEntity: List<PokemonDetailEntity.StatEntity>): String {
        val type = object : TypeToken<List<PokemonDetailEntity.StatEntity>>() {}.type
        return Gson().toJson(statEntity, type)
    }

    @TypeConverter
    fun fromJsonStringStatEntity(json: String): List<PokemonDetailEntity.StatEntity> {
        val type = object : TypeToken<List<PokemonDetailEntity.StatEntity>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromSprites(sprites: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(sprites, type)
    }

    @TypeConverter
    fun fromJsonStringSprite(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}