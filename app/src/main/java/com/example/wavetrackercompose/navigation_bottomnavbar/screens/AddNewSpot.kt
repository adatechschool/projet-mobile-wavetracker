package com.example.wavetrackercompose.navigation_bottomnavbar.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var testname by rememberSaveable { mutableStateOf("") }
    // Contenu de la page
    Column(
        modifier = Modifier
            .padding(8.dp, 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.size(16.dp),
            text = "Add a new surf spot"
        )
        Text(
            text = "Surf spot name"
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = testname,
            onValueChange = { testname = it },
            placeholder = { Text(text = "ex: Pointe de Lizay") }
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
