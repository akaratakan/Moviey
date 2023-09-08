package com.aa.base.ui.compose.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aa.base.R
import com.aa.base.ui.compose.common.ErrorPopUp
import com.aa.base.ui.compose.common.ProgressScreen
import com.aa.base.ui.configuration.AppTheme
import com.aa.base.ui.configuration.shapes
import com.aa.base.ui.configuration.shimmerBrush
import com.aa.base.ui.viewmodels.SearchViewModel
import com.aa.model.generic.Magic
import com.aa.model.search.Search
import com.aa.model.search.SearchResponse

/**
 * Created by atakanakar on 18.08.2023
 * */


@Composable
fun SearchScreen(
    onFieldActive: (Boolean) -> Unit,
    onMovieItemClicked: (imdbID: String) -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val resultState = viewModel.resultFlow.collectAsState(initial = null)

    AppTheme {
        SearchContainer(
            resultState = resultState.value,
            onSearch = { viewModel.searchMovie(it) },
            onFieldActive = { onFieldActive(it) },
            onMovieItemClicked = { onMovieItemClicked(it) })
    }
}

@Composable
fun SearchContainer(
    resultState: Magic<SearchResponse>?,
    onSearch: (name: String) -> Unit,
    onFieldActive: (Boolean) -> Unit,
    onMovieItemClicked: (imdbID: String) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchField(
                onFieldActive = { onFieldActive(it) },
                onSearch = { onSearch(it) }
            )
            when (resultState) {
                is Magic.Progress -> {
                    ProgressScreen()
                }

                is Magic.Success -> {
                    MovieList(resultState.data.search, onMovieItemClicked)
                }

                is Magic.Failure -> {
                    ErrorPopUp(errorText = resultState.errorMessage)
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    onFieldActive: (Boolean) -> Unit,
    onSearch: (name: String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    onFieldActive(isSearchActive)
    SearchBar(
        query = searchText,
        onQueryChange = { newText -> searchText = newText },
        onSearch = { query ->
            onSearch(query)
            isSearchActive = false
        },
        active = isSearchActive,
        onActiveChange = { isActive -> isSearchActive = isActive },

        modifier = if (isSearchActive) {
            Modifier.fillMaxWidth()
        } else {
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            if (isSearchActive) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.localized_description),
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            isSearchActive = false
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.localized_description),
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        searchText = ""
                    }
            )
        },
        tonalElevation = 4.dp,
        content = {}
    )
}


@Composable
fun MovieList(
    movies: List<Search>,
    onMovieItemClicked: (imdbID: String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie, onMovieItemClicked)
        }
    }
}

@Composable
fun MovieItem(
    item: Search,
    onMovieItemClicked: (imdbID: String) -> Unit
) {
    val showShimmer = remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .clip(shapes.extraLarge)
            .clickable {
                onMovieItemClicked(item.imdbID)
            },
        shape = shapes.extraLarge,
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .shadow(1.dp)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.poster)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onSuccess = { showShimmer.value = false }
            )
        }
    }
}
