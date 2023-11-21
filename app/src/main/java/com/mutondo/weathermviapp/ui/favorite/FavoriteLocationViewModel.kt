package com.mutondo.weathermviapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.domain.repositories.FavoriteLocationRepository
import com.mutondo.weathermviapp.ui.favorite.FavoriteLocationResult.GetAllFavoritesResult
import com.mutondo.weathermviapp.ui.favorite.FavoriteLocationResult.SaveFavoriteResult
import com.mutondo.weathermviapp.ui.mvibase.MviViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteLocationViewModel @Inject constructor(
    private val repository: FavoriteLocationRepository
) : ViewModel() , MviViewModel<FavoriteLocationIntent, FavoriteLocationViewState, FavoriteLocationResult> {

    private var currentViewState = FavoriteLocationViewState.default()

    private val mviIntent: MutableLiveData<FavoriteLocationIntent> by lazy {
        MutableLiveData()
    }

    val viewState: LiveData<FavoriteLocationViewState> = mviIntent.switchMap {
        intent -> processIntent(intent).map {
            reduce(currentViewState, it)
        }
    }

    fun setMviIntent(intent: FavoriteLocationIntent) {
        mviIntent.value = intent
    }

    override fun processIntent(intent: FavoriteLocationIntent)
    : LiveData<FavoriteLocationResult> = liveData {
        when(intent) {
            is FavoriteLocationIntent.GetAllFavoritesIntent -> {
                emit(GetAllFavoritesResult.Loading)
                emit(getAllFavorites())
            }

            is FavoriteLocationIntent.SaveFavoriteIntent -> {
                emit(saveFavorite(intent.favorite))
            }
        }
    }

    override fun reduce(state: FavoriteLocationViewState, result: FavoriteLocationResult): FavoriteLocationViewState {
        when (result) {
            is GetAllFavoritesResult -> when (result) {

                is GetAllFavoritesResult.Loading -> {
                    currentViewState = state.copy(isLoading = true)
                    return currentViewState
                }
                is GetAllFavoritesResult.Success -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        favorites = result.favorites
                    )
                    return currentViewState
                }
                is GetAllFavoritesResult.Failure -> {
                    currentViewState = state.copy(
                        isLoading = false,
                        favorites = emptyList()
                    )
                    return currentViewState
                }
            }

            is SaveFavoriteResult -> when (result) {
                is SaveFavoriteResult.Success -> {
                    currentViewState = state.copy(
                        rowId = result.rowId
                    )
                    return currentViewState
                }
            }
        }
    }

    private suspend fun getAllFavorites(): FavoriteLocationResult {
       return repository.getAllFavorites().map {
           GetAllFavoritesResult.Success(it)
       }.first()
    }

    private suspend fun saveFavorite(favorite: FavoriteLocation): FavoriteLocationResult {
        return SaveFavoriteResult.Success(repository.insert(favorite))
    }

}