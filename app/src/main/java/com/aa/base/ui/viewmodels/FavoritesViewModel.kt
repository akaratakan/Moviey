package com.aa.base.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.aa.base.ui.root.BaseViewModel
import com.aa.usecase.detail.DetailDbUseCase
import com.aa.usecase.fav.FetchFavsUseCase
import com.aa.local.entities.movie.MovieDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val fetchFavsUseCase: FetchFavsUseCase,
    private val detailDbUseCase: DetailDbUseCase
) : BaseViewModel() {

    val moviesFlow = fetchFavsUseCase.resultFlow
    fun fetchAllFavMovies() {
        viewModelScope.launch {
            fetchFavsUseCase.launch()
        }
    }

    val dbActionFlow = detailDbUseCase.resultFlow
    fun makeDbAction(entity: MovieDetailEntity, active: Boolean) {
        detailDbUseCase.init(isFavAction = active, entity = entity)
        viewModelScope.launch {
            detailDbUseCase.launch()
        }
    }

}
