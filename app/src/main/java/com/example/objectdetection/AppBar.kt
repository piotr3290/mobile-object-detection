package com.example.objectdetection

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val title: String =
        if (navBackStackEntry?.destination?.route != null) navBackStackEntry!!.destination.route.toString() else ""
    val isMainScreen: Boolean = navBackStackEntry?.destination?.route == Screen.MainScreen.route
    CenterAlignedTopAppBar(
        title = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            if (!isMainScreen) {
                BackIcon(navController = navController)
            }
        }
    )
}