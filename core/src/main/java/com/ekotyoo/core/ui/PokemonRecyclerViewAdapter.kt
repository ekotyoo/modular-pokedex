package com.ekotyoo.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekotyoo.core.databinding.PokemonListItemBinding
import com.ekotyoo.core.domain.model.Pokemon

class PokemonRecyclerViewAdapter(private val onItemClickCallback: OnItemClickCallback) :
    ListAdapter<Pokemon, PokemonRecyclerViewAdapter.ViewHolder>(
        DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(PokemonListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    inner class ViewHolder(private val binding: PokemonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.apply {
                tvPokemonName.text = pokemon.nameFormatted
                Glide.with(binding.root.context)
                    .load(pokemon.imageUrl)
                    .fitCenter()
                    .into(ivPokemon)
                root.setOnClickListener {
                    onItemClickCallback.onClick(pokemon)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onClick(pokemon: Pokemon)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.imageUrl == oldItem.imageUrl
            }
        }
    }
}