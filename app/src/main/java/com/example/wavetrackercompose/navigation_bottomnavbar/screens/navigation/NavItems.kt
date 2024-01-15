package com.example.wavetrackercompose.navigation_bottomnavbar.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems: List<NavItem> = listOf(
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomePage.name
    ),
    NavItem(
        label = "Add a new spot",
        icon = Icons.Default.Add,
        route = Screens.AddNewSpot.name
    )
)