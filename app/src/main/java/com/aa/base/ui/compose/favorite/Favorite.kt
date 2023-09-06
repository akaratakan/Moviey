package com.aa.base.ui.compose.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aa.base.R
import com.aa.base.ui.compose.detail.FavIcon
import com.aa.base.ui.compose.search.ErrorText
import com.aa.base.ui.compose.search.ProgressScreen
import com.aa.base.ui.configuration.AppTheme
import com.aa.local.entities.movie.MovieDetailEntity
import com.aa.model.generic.Magic


@Composable
fun FavoritesScreen(onMovieItemClicked: (imdbID: String) -> Unit) {
    val viewModel: FavoritesViewModel = hiltViewModel()
    val resultState = viewModel.moviesFlow.collectAsState(initial = Magic.loading())
    viewModel.dbActionFlow.collectAsState(initial = null)

    LaunchedEffect(Unit) { viewModel.fetchAllFavMovies() }

    AppTheme {
        FavContainer(
            resultState = resultState.value,
            onFav = { entity, isFav -> viewModel.makeDbAction(entity, isFav) },
            onMovieItemClicked = { onMovieItemClicked(it.imdbID) }
        )
    }
}

@Composable
fun FavContainer(
    resultState: Magic<List<MovieDetailEntity>>,
    onFav: (entity: MovieDetailEntity, isFav: Boolean) -> Unit,
    onMovieItemClicked: (MovieDetailEntity) -> Unit
) {
    when (resultState) {
        is Magic.Progress -> {
            ProgressScreen()
        }

        is Magic.Success -> {
            FavList(
                movies = resultState.data,
                onFav = { entity, isFav -> onFav(entity, isFav) },
                onMovieSelected = { entity -> onMovieItemClicked(entity) })
        }

        is Magic.Failure -> {
            ErrorText("Failure: ${resultState.errorMessage}")
        }

        else -> {}
    }
}

@Composable
fun FavList(
    movies: List<MovieDetailEntity>,
    onFav: (entity: MovieDetailEntity, isFav: Boolean) -> Unit,
    onMovieSelected: (entity: MovieDetailEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) { movie ->
            FavItem(movie, onFav = { onFav(movie, it) }, onClick = { onMovieSelected(movie) })
        }
    }
}

@Composable
fun FavItem(
    movie: MovieDetailEntity,
    onFav: (isFav: Boolean) -> Unit,
    onClick: (MovieDetailEntity) -> Unit
) {
    val isFavState = remember { mutableStateOf(movie.isFav) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                onClick(movie)
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.wrapContentSize()) {
                AsyncImage(
                    modifier = Modifier
                        .height(height = 240.dp)
                        .aspectRatio(2 / 3f)
                        .shadow(1.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                FavIcon(isFav = isFavState.value) {
                    isFavState.value = it
                    onFav(it)
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                MovieContentItem(name = movie.title, icon = Icons.Filled.Movie)
                MovieContentItem(
                    name = movie.director ?: "N/A",
                    icon = Icons.Filled.VideoCameraBack
                )
                MovieContentItem(name = movie.imdbRating ?: "N/A", icon = Icons.Filled.StarRate)
            }
        }
    }
}

@Composable
fun MovieContentItem(name: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "content_vector"
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(text = name, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCardItem() {

    val isFavState = remember { mutableStateOf(true) }
    LazyColumn {
        items(1) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.wrapContentSize()) {
                        Image(
                            modifier = Modifier
                                .height(height = 240.dp)
                                .aspectRatio(2 / 3f)
                                .shadow(1.dp),
                            imageVector = Icons.Filled.FileOpen,
                            contentDescription = ""
                        )
                        FavIcon(isFav = isFavState.value) {
                            isFavState.value = it
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        MovieContentItem(name = "Kill BillBill", icon = Icons.Filled.Movie)
                        MovieContentItem(name = "Tarantino", icon = Icons.Filled.VideoCameraBack)
                        MovieContentItem(name = "5.4", icon = Icons.Filled.StarRate)
                    }
                }
            }
        }
    }

}