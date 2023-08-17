package com.aa.base.ui.fragment.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aa.base.ui.root.BaseViewModel
import com.aa.data_usecase.FetchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMovieUseCase: FetchMovieUseCase
) : BaseViewModel() {

    val fetchMovieLD = fetchMovieUseCase.resultFlow.asLiveData()
    fun searchMovie(name: String) {
        fetchMovieUseCase.initName(name)
        viewModelScope.launch {
            fetchMovieUseCase.launch()
        }
    }

}