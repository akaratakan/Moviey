package com.aa.base.ui.compose.detail

import androidx.lifecycle.viewModelScope
import com.aa.base.ui.root.BaseViewModel
import com.aa.data_usecase.FetchMovieUseCase
import com.aa.data_usecase.detail.DetailDbUseCase
import com.aa.model.movie.MovieDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by atakanakar on 23.08.2023
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchMovieUseCase: FetchMovieUseCase,
    private val detailDbUseCase: DetailDbUseCase
) : BaseViewModel() {

    val fetchFlow = fetchMovieUseCase.resultFlow

    fun fetchMovie(name: String) {
        fetchMovieUseCase.initName(name)
        viewModelScope.launch {
            fetchMovieUseCase.launch()
        }
    }

    val dbFlow = detailDbUseCase.resultFlow

    init {
        viewModelScope.launch {
            dbFlow.collect {
                Timber.e(it.toString())
            }
        }
    }


    fun makeDbAction(response: MovieDetailResponse, active: Boolean) {
        detailDbUseCase.init(isFavAction = active, detailResponse = response)
        viewModelScope.launch {
            detailDbUseCase.launch()
        }
    }

}