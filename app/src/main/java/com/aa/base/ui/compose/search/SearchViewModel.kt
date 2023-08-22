package com.aa.base.ui.compose.search

import androidx.lifecycle.viewModelScope
import com.aa.base.ui.root.BaseViewModel
import com.aa.data_usecase.SearchMovieUseCase
import com.aa.data_usecase.base.SearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by atakanakar on 19.08.2023
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
//    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {
    /*
        private val _state = MutableStateFlow<Magic<SearchResponse>>(Magic.initial())
        val state: StateFlow<Magic<SearchResponse>> = _state

        init {
            handleSearch()
        }
        fun handleSearch() {
            viewModelScope.launch {
                searchMovieUseCase.resultFlow.map {
                    if (it!=null) _state.value = it
                }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1).coll
            }
        }*/

    val resultFlow = searchMovieUseCase.resultFlow
    fun searchMovie(name: String) {
//        getAllSearchedText()
        searchMovieUseCase.initName(name)
        viewModelScope.launch {
            searchMovieUseCase.launch()
        }
    }

//    private fun handleTest() {
//        viewModelScope.launch {
//            searchHistoryUseCase.resultFlow.map {
//                Timber.e(it.toString())
//            }
//        }
//    }
//
//    fun getAllSearchedText() {
//        viewModelScope.launch {
//            searchHistoryUseCase.launch()
//        }
//    }
//
//    init {
//        handleTest()
//    }

}