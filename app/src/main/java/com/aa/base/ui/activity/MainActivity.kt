package com.aa.base.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aa.base.ui.compose.search.SearchScreen
import com.aa.base.ui.compose.search.SearchViewModel
import com.aa.base.ui.configuration.AppTheme
import com.aa.base.ui.configuration.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val searchVM: SearchViewModel = hiltViewModel()
            AppTheme {
                BaseScreen(searchVM)
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = {
                navController.navigate("info")
            }) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = {
                navController.navigate("favorite")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "Localized description",
                )
            }
        },
        containerColor = darkSecondary,
        contentColor = darkOnPrimary,
        floatingActionButton = {
            FloatingActionButton(
                shape = ShapeDefaults.Large,
                onClick = {
                    navController.navigate("search")
                },
                containerColor = darkPrimary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun BaseScreen(searchVM: SearchViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = "search"
        ) {
            composable("info") { Text("atakan") }
            composable("favorite") { Text("akar") }
            composable("search") { SearchScreen(searchVM) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMain() {
    AppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = "info"
            ) {
                composable("info") { Text("atakan") }
                composable("favorite") { Text("akar") }
//                composable("search") { SearchScreen(fakeVM) }
            }
        }
    }
}

