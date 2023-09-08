package com.aa.base.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aa.base.ui.compose.detail.DetailScreen
import com.aa.base.ui.compose.favorite.FavoritesScreen
import com.aa.base.ui.compose.search.SearchScreen
import com.aa.base.ui.configuration.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                BaseScreen()
            }
        }
    }
}

@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    var isBottomBarVisible by remember { mutableStateOf(true) }

    val animateEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { it })
    val animateExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { -it })

    val animatePopEnter: EnterTransition = fadeIn() + slideInHorizontally(initialOffsetX = { -it })
    val animatePopExit: ExitTransition = fadeOut() + slideOutHorizontally(targetOffsetX = { it })

    Scaffold(bottomBar = {
        AnimatedVisibility(
            visible = isBottomBarVisible, enter = expandVertically(), exit = shrinkVertically()
        ) {
            BottomBar(navController = navController)
        }
    }) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Screen.Search.route
        ) {
            composable("${Screen.MovieDetail.route}/{imdb_id}",
                enterTransition = { animateEnter },
                exitTransition = { animateExit },
                popEnterTransition = { animatePopEnter },
                popExitTransition = { animatePopExit }) { backStackEntry ->
                isBottomBarVisible = false
                DetailScreen(backStackEntry.arguments?.getString("imdb_id") ?: "")
            }
            composable(Screen.Favorites.route) {
                isBottomBarVisible = true
                FavoritesScreen {
                    navController.navigate(route = "${Screen.MovieDetail.route}/${it}") {
                        launchSingleTop = true
                    }
                }
            }
            composable(route = Screen.Search.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }) {
                SearchScreen(onFieldActive = { isBottomBarVisible = !it }) {
                    navController.navigate(route = "${Screen.MovieDetail.route}/${it}") {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(modifier = Modifier.fillMaxWidth(), actions = {
        IconButton(onClick = {
            navController.navigate(Screen.Favorites.route) {
                launchSingleTop = true
                popUpTo(Screen.Search.route)
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Localized description",
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(
            shape = ShapeDefaults.Large, onClick = {
                navController.navigate(Screen.Search.route) {
                    launchSingleTop = true
                    popUpTo(Screen.Search.route)
                }
            }, elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
        ) {
            Icon(
                imageVector = Screen.Search.menuImage,
                contentDescription = "Localized description"
            )
        }
    })
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMain() {
    AppTheme {
        val navController = rememberNavController()
        Scaffold(bottomBar = { BottomBar(navController = navController) }) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = Screen.Search.route
            ) {
                composable(Screen.Favorites.name) { Text(Screen.Info.route) }
                composable(Screen.Search.name) {
                    SearchScreen(onFieldActive = {}, onMovieItemClicked = {})
                }
            }
        }
    }
}