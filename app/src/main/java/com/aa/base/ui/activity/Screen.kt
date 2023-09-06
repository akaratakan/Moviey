package com.aa.base.ui.activity

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val name: String, val menuImage: ImageVector) {
    data object Info : Screen("info", "Info", Icons.Filled.Info)
    data object Favorites : Screen("favorites", "Favorites", Icons.Filled.Favorite)
    data object Search : Screen("search", "Search", Icons.Filled.Search)
    data object MovieDetail : Screen("detail", "Detail", Icons.Filled.Search)
}


