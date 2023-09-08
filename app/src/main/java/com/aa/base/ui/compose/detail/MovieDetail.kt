package com.aa.base.ui.compose.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aa.base.R
import com.aa.base.ui.compose.common.ErrorPopUp
import com.aa.base.ui.compose.common.ProgressScreen
import com.aa.base.ui.configuration.AppTheme
import com.aa.base.ui.viewmodels.DetailViewModel
import com.aa.model.generic.Magic
import com.aa.model.generic.mapData
import com.aa.model.movie.MovieDetailResponse

/**
 * Created by atakanakar on 22.08.2023
 */

@Composable
fun DetailScreen(imdbId: String) {

    val viewModel: DetailViewModel = hiltViewModel()

    LaunchedEffect(imdbId) { viewModel.fetchMovie(imdbId) }
    val resultState = viewModel.fetchFlow.collectAsState(initial = Magic.loading())
    viewModel.dbFlow.collectAsState(initial = null)
    AppTheme {
        DetailLayout(resultState.value) { isFav ->
            resultState.value.mapData { data ->
                viewModel.makeDbAction(data, isFav)
            }
        }
    }
}

@Composable
fun DetailLayout(resultState: Magic<MovieDetailResponse>, onFav: (isFav: Boolean) -> Unit) {
    when (resultState) {
        is Magic.Progress -> { ProgressScreen() }
        is Magic.Success -> { MovieDetailScreen(resultState.data) { onFav(it) } }
        is Magic.Failure -> { ErrorPopUp(resultState.errorMessage) }
        else -> {}
    }

}

@Composable
fun MovieDetailScreen(resultState: MovieDetailResponse, onFav: (isFav: Boolean) -> Unit) {
    val isFavState = remember { mutableStateOf(resultState.isFav) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        DetailTop(poster = resultState.poster, isFav = isFavState.value) {
            isFavState.value = it
            onFav(it)
        }

        Spacer(modifier = Modifier.padding(16.dp))
        TypeChips(types = resultState.genre?.split(",") ?: emptyList())
        Spacer(modifier = Modifier.padding(8.dp))
        MoviePlot(title = resultState.title, plot = resultState.plot)
    }
}


@Composable
fun DetailTop(poster: String, isFav: Boolean, onFav: (isFav: Boolean) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .shadow(1.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(poster)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        FavIcon(isFav = isFav, onFavClick = {
            onFav(it)
        })
    }

}

@Composable
fun FavIcon(isFav: Boolean, onFavClick: (Boolean) -> Unit) {
    val iconVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    val contentDescription = if (isFav) "Active fav" else "Inactive fav"
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        modifier = Modifier
            .wrapContentSize(Alignment.BottomEnd)
            .clickable(
                onClick = {
                    onFavClick(!isFav)
                },
                indication = rememberRipple(color = Color.Gray, bounded = true, radius = 24.dp),
                interactionSource = interactionSource
            )
            .clip(CircleShape)
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .padding(8.dp),
        imageVector = iconVector,
        contentDescription = contentDescription,
    )
}

@Composable
fun TypeChips(types: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(types) { type ->
            ChipItem(type = type)
        }
    }
}

@Composable
fun ChipItem(type: String) {
    BoxWithConstraints(
        modifier = Modifier
            .background(color = colorScheme.inversePrimary, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Text(
            text = type,
            modifier = Modifier.wrapContentWidth(),
            color = Color.White,
            maxLines = 1
        )
    }
}

@Composable
fun MoviePlot(title: String, plot: String) {
    Column {
        Text(text = title, style = typography.titleMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = plot, style = typography.labelMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    val poster =
        "https://m.media-amazon.com/images/M/MV5BZDEyN2NhMjgtMjdhNi00MmNlLWE5YTgtZGE4MzNjMTRlMGEwXkEyXkFqcGdeQXVyNDUyOTg3Njg@._V1_SX300.jpg\""
    val chips = listOf("Action", "Sci-fi", "Adventure")
    val plot =
        "When his brother is killed in a robbery, paraplegic Marine Jake Sully decides to take his place in a mission on the distant world of Pandora. There he learns of greedy corporate figurehead Parker Selfridge's intentions of driving off the native humanoid \\\"Na'vi\\\" in order to mine for the precious material scattered throughout their rich woodland. In exchange for the spinal surgery that will fix his legs, Jake gathers knowledge, of the Indigenous Race and their Culture, for the cooperating military unit spearheaded by gung-ho Colonel Quaritch, while simultaneously attempting to infiltrate the Na'vi people with the use of an \\\"avatar\\\" identity. While Jake begins to bond with the native tribe and quickly falls in love with the beautiful alien Neytiri, the restless Colonel moves forward with his ruthless extermination tactics, forcing the soldier to take a stand - and fight back in an epic battle for the fate of Pandora."

    AppTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .aspectRatio(2 / 3f)
                    .shadow(1.dp)
            ) {

            }
            if (true) {
                Icon(
                    modifier = Modifier
                        .wrapContentSize(Alignment.BottomEnd),
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Inactive fav",
                )
            } else {
                Icon(
                    Icons.Outlined.Favorite,
                    contentDescription = "Active fav",
                )
            }

        }
    }
}