package com.ekotyoo.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(

	@SerializedName("next")
	val next: String?,

	@SerializedName("previous")
	val previous: String?,

	@SerializedName("count")
	val count: Int,

	@SerializedName("results")
	val results: List<PokemonListItem>
)

data class PokemonListItem(

	@SerializedName("name")
	val name: String,

	@SerializedName("url")
	val url: String
)
