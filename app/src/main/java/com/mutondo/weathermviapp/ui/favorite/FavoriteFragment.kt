package com.mutondo.weathermviapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutondo.weathermviapp.databinding.FragmentFavoritesBinding
import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.ui.main.BaseFragment
import com.mutondo.weathermviapp.ui.mvibase.MviView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment(), MviView<FavoriteLocationViewState> {

    private var binding: FragmentFavoritesBinding? = null

    @Inject
    lateinit var viewModel: FavoriteLocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarTitle("Favorite Locations")
        subscribeObservers()
        triggerGetAllFavoritesIntent()
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, this::render)
    }

    override fun render(state: FavoriteLocationViewState) {
        binding!!.progress.visibility = if(state.isLoading) View.VISIBLE else View.INVISIBLE

        // Check for error

        if(state.favorites.isEmpty()) {
            showNoFavorites()
        } else {
            setupRecyclerView(state.favorites)
        }
    }

    private fun showSavedMessage() {
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView(locations: List<FavoriteLocation>) {

        with(binding!!) {
            val layoutManager = LinearLayoutManager(context)

            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.hasFixedSize()
            recyclerview.adapter = FavoriteAdapter(locations)
            recyclerview.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
        }
    }

    private fun showNoFavorites() {
//        TODO("Not yet implemented")
    }

    private fun triggerGetAllFavoritesIntent() {
        viewModel.setMviIntent(FavoriteLocationIntent.GetAllFavoritesIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TAG = "FavoriteFragment"
    }
}