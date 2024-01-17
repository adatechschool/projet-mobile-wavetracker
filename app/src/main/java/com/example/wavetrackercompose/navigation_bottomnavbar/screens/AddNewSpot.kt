package com.example.wavetrackercompose.navigation_bottomnavbar.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


// Fonction qui permet de naviguer vers l'écran d'accueil
// (navigateUp() renvoie vers l'écran précédent).
@Composable
fun AddNewSpot(navController: NavHostController) {
    // Contenu de la page
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add a new spot",
            fontFamily = FontFamily.Serif,
            fontSize = 22.sp
        )
    }
    // Style du bouton
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "Home")
        }
    }
}