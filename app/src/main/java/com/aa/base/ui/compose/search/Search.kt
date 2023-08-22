package com.aa.base.ui.compose.search

import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aa.base.R
import com.aa.base.ui.configuration.AppTheme
import com.aa.base.ui.configuration.darkSecondary
import com.aa.base.ui.configuration.darkSurface
import com.aa.base.ui.configuration.purple200
import com.aa.base.ui.configuration.shapes
import com.aa.model.generic.Magic
import com.aa.model.search.Search
import com.aa.model.search.SearchResponse
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Created by atakanakar on 18.08.2023
 * */


@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val resultState = viewModel.resultFlow.collectAsState(initial = null)


    AppTheme {
        SearchContainer(resultState.value) {
            viewModel.searchMovie(it)
        }
    }
}

@Composable
fun SearchContainer(resultState: Magic<SearchResponse>?, onSearch: (name: String) -> Unit) {
    Surface(modifier = Modifier.fillMaxSize().background(color = darkSecondary)) {
        Column(modifier = Modifier.fillMaxSize().background(color = darkSecondary).padding(horizontal = 8.dp, vertical = 8.dp)) {
            SearchField {
                onSearch(it)
            }
            when (resultState) {
                is Magic.Initial -> {
                    Text("")
                }

                is Magic.Progress -> {
                    ProgressScreen()
                }

                is Magic.Success -> {
                    MovieList(resultState.data.search)
                }

                is Magic.Failure -> {
                    ErrorText("Failure: ${resultState.errorMessage}")
                }

                else -> {
                    Text("")
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(onSearch: (name: String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    SearchBar(
        query = searchText,
        onQueryChange = { newText -> searchText = newText },
        onSearch = { query ->
            onSearch(query)
            isSearchActive = false
        },
        active = isSearchActive,
        onActiveChange = { isActive -> isSearchActive = isActive },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Localized description"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.localized_description),
                modifier = Modifier.clickable {
                    searchText = ""
                }
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = darkSurface,
            dividerColor = Color.Gray
        ),
        tonalElevation = 4.dp,
        content = {
            /*LazyColumn {
                items(3) {
                    Text("test")
                }
            }*/
        }
    )
}


@Composable
fun MovieList(movies: List<Search>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Sütun sayısını belirleyin
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem2(movie)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(item: Search) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(context, item.title, Toast.LENGTH_SHORT)
                    .show()
            },
        colors = CardDefaults.cardColors(containerColor = purple200),
        shape = shapes.extraLarge,
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = item.poster,
                contentScale = ContentScale.Fit,
                contentDescription = "movie thumb"
            )
            Column(modifier = Modifier.padding(4.dp)) {
                TitleAndDescription(title = "Movie", desc = item.title)
                TitleAndDescription(title = "Type", desc = item.type)
                TitleAndDescription(title = "Year", desc = item.year)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem2(item: Search) {
    val context = LocalContext.current
    val drawable: Drawable? = getDrawable(context, R.drawable.ic_dashboard_black_24dp)
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                Toast
                    .makeText(context, item.title, Toast.LENGTH_SHORT)
                    .show()
            },/*
        colors = CardDefaults.cardColors(containerColor = purple200),*/
        shape = shapes.extraLarge,
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .shadow(1.dp).padding(8.dp),
                model = item.poster,
                contentScale = ContentScale.Fit,
                contentDescription = "movie thumb",

                ) {

                it.addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        target?.onLoadFailed(drawable)

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
            }
//            Text(text = item.title,color = Color.White)
        }
    }
}

@Composable
fun ErrorText(errorText: String) {
    Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
        Text(text = errorText)
    }
}


@Composable
fun ProgressScreen() {
    val rotation = rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            progress = 0.5f,
            color = Color.Blue,
            strokeWidth = 8.dp,
            modifier = Modifier.rotate(rotation.value) // Dönme animasyonunu uygula
        )
    }
}

@Composable
fun TitleAndDescription(title: String, desc: String) {
    Row {
        Text(text = "$title:     ")
        Text(
            text = desc,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxSize()
        )
    }
}


//@Preview
//@Composable
//private fun PreviewSearchScreen() {
//    AppTheme {
////        SearchScreen(viewModel = hiltViewModel())
////        ProgressScreen()
////        SearchField(onSearch = {})
////        MovieItem(item = Search())
//    }
//}

/*


// The UI for each list item can be generated by a reusable composable
@Composable
fun MySimpleListItem(itemViewState: MovieListItem) {
    Text(text = "itemViewState")
}
*/

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen1() {
    AppTheme {
        TitleAndDescription(
            title = "atalan",
            desc = "ajkshdkjahdjkahkjdaskjdnsakjndkjabndkjanbdkjbaskjdbhkjabhdkjashdkjha"
        )
    }
}
