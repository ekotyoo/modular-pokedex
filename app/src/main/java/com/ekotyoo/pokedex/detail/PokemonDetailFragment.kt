package com.ekotyoo.pokedex.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.domain.model.PokemonDetail
import com.ekotyoo.pokedex.R
import com.ekotyoo.pokedex.databinding.FragmentPokemonDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val args: PokemonDetailFragmentArgs by navArgs()
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonDetailViewModel.setPokemonName(args.name)
        pokemonDetailViewModel.pokemonDetail.observe(viewLifecycleOwner, Observer(::setPokemonData))
    }

    private fun setPokemonData(pokemonDetail: Resource<PokemonDetail?>) {
        pokemonDetail.let {
            when (it) {
                is Resource.Error -> {
                    binding.loadingIndicator.visibility = View.INVISIBLE
                    findNavController().navigateUp()
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingIndicator.visibility = View.INVISIBLE
                    it.data?.let { data ->
                        Glide.with(requireContext()).load(data.imageUrl).into(binding.ivPokemon)
                        binding.apply {
                            tvPokemonName.text = data.nameFormatted
                            progressStat1.setProgress(data.stats[0].baseStat, true)
                            progressStat2.setProgress(data.stats[1].baseStat, true)
                            progressStat3.setProgress(data.stats[2].baseStat, true)
                            tvStat1.text = data.stats[0].name.replaceFirstChar { c -> c.uppercaseChar() }
                            tvStat2.text = data.stats[1].name.replaceFirstChar { c -> c.uppercaseChar() }
                            tvStat3.text = data.stats[2].name.replaceFirstChar { c -> c.uppercaseChar() }
                            tvWeight.text = getString(R.string.weight, data.weight)
                            tvHeight.text = getString(R.string.height, data.height)
                            tvBaseExperience.text =
                                getString(R.string.base_experience, data.baseExperience)
                            data.sprites.forEach { spriteUrl ->
                                val iv = ImageView(requireContext()).apply {
                                    minimumHeight = 300
                                    minimumWidth = 300
                                }
                                Glide.with(requireContext()).load(spriteUrl).fitCenter().into(iv)
                                binding.llSprites.addView(iv)
                            }
                            ibFavorite.setImageResource(if (data.isFavorite) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border)
                            ibFavorite.setOnClickListener {
                                val state = !data.isFavorite
                                pokemonDetailViewModel.addPokemonToFavorite(data, state)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}