package com.ekotyoo.feature_favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.ui.PokemonRecyclerViewAdapter
import com.ekotyoo.feature_favorite.databinding.FragmentFavoriteBinding
import com.ekotyoo.pokedex.MainActivity
import com.ekotyoo.pokedex.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private lateinit var pokemonAdapter: PokemonRecyclerViewAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(EntryPointAccessors.fromApplication(
                requireActivity().applicationContext, FavoriteModuleDependencies::class.java)
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(com.ekotyoo.pokedex.R.string.favorite)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).supportActionBar?.title = getString(com.ekotyoo.pokedex.R.string.app_name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        pokemonAdapter =
            PokemonRecyclerViewAdapter(object : PokemonRecyclerViewAdapter.OnItemClickCallback {
                override fun onClick(pokemon: Pokemon) {
                    findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToPokemonDetailFragment(
                        pokemon.name))
                }
            })

        with(binding.rvPokemon) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = pokemonAdapter
        }
    }

    private fun observeViewModel() {
        favoriteViewModel.favoritePokemons.observe(viewLifecycleOwner, Observer(::setPokemonList))
    }

    private fun setPokemonList(pokemons: List<Pokemon>) {
        pokemons.let {
            pokemonAdapter.submitList(pokemons)
        }
    }
}