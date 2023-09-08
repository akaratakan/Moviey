package com.aa.base.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.aa.base.ui.root.BaseViewModel
import com.aa.data_usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by atakanakar on 19.08.2023
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {

    val resultFlow = searchMovieUseCase.resultFlow
    fun searchMovie(name: String) {
        searchMovieUseCase.initName(name)
        viewModelScope.launch {
            searchMovieUseCase.launch()
        }
    }

}