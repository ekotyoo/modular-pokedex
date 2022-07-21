package com.ekotyoo.pokedex.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ekotyoo.core.data.Resource
import com.ekotyoo.core.domain.model.Pokemon
import com.ekotyoo.core.ui.PokemonRecyclerViewAdapter
import com.ekotyoo.pokedex.R
import com.ekotyoo.pokedex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private val pokemonListViewModel: PokemonListViewModel by viewModels()

    private lateinit var pokemonAdapter: PokemonRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        initRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvPokemon.adapter = null
        _binding = null
    }

    private fun observeViewModel() {
        pokemonListViewModel.pokemons.observe(viewLifecycleOwner, Observer(::setPokemonList))
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_favorites -> {
                        findNavController().navigate(PokemonListFragmentDirections.actionPokemonFragmentToFavoriteFragment())
                    }
                    R.id.menu_settings -> {
                        findNavController().navigate(PokemonListFragmentDirections.actionPokemonFragmentToSettingsFragment())
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun initRecyclerView() {
        pokemonAdapter =
            PokemonRecyclerViewAdapter(object : PokemonRecyclerViewAdapter.OnItemClickCallback {
                override fun onClick(pokemon: Pokemon) {
                    val action =
                        PokemonListFragmentDirections.actionPokemonFragmentToPokemonDetailFragment(
                            pokemon.name)
                    findNavController().navigate(action)
                }
            })

        with(binding.rvPokemon) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = pokemonAdapter
        }
    }

    private fun setPokemonList(pokemons: Resource<List<Pokemon>>) {
        pokemons.let {
            when (it) {
                is Resource.Error -> {
                    binding.loadingIndicator.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingIndicator.visibility = View.INVISIBLE
                    pokemonAdapter.submitList(it.data)
                }
            }
        }
    }
}