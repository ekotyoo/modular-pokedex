package com.ekotyoo.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(

	@SerializedName("stats")
	val stats: List<StatsItem>,

	@SerializedName("base_experience")
	val baseExperience: Int,

	@SerializedName("name")
	val name: String,

	@SerializedName("weight")
	val weight: Int,

	@SerializedName("id")
	val id: Int,

	@SerializedName("sprites")
	val sprites: Sprites,

	@SerializedName("height")
	val height: Int,
)

data class Stat(

	@SerializedName("name")
	val name: String,

	@SerializedName("url")
	val url: String,
)

data class Sprites(

	@SerializedName("back_shiny_female")
	val backShinyFemale: String?,

	@SerializedName("back_female")
	val backFemale: String?,

	@SerializedName("back_default")
	val backDefault: String?,

	@SerializedName("front_shiny_female")
	val frontShinyFemale: String?,

	@SerializedName("front_default")
	val frontDefault: String?,

	@SerializedName("front_female")
	val frontFemale: String?,

	@SerializedName("back_shiny")
	val backShiny: String?,

	@SerializedName("front_shiny")
	val frontShiny: String?,
) {
    fun getData(): List<String> = listOfNotNull(
		backShinyFemale,
		backFemale,
		backDefault,
		frontShinyFemale,
		frontDefault,
		frontFemale,
		backShiny,
		frontShiny
	)
}

data class StatsItem(

	@SerializedName("stat")
	val stat: Stat,

	@SerializedName("base_stat")
	val baseStat: Int,

	@SerializedName("effort")
	val effort: Int,
)
