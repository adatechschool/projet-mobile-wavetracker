//MainActivity.kt

package com.example.wavetrackercompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wavetrackercompose.ui.theme.WaveTrackerComposeTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wavetrackercompose.model.Spots
import com.example.wavetrackercompose.network.SpotsApi
import kotlinx.coroutines.runBlocking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.wavetrackercompose.navigation_bottomnavbar.screens.AddNewSpot
import com.example.wavetrackercompose.navigation_bottomnavbar.screens.BackToHomeButton
import coil.compose.rememberAsyncImagePainter
import com.example.wavetrackercompose.model.SpotDetails
import com.example.wavetrackercompose.model.SpotList


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveTrackerComposeTheme {
                val navController = rememberNavController()
                val spots = runBlocking {
                    SpotsApi.getSpots()
                }
/*                SootheBottomNavigation(navController)*/
                // Routes
                NavHost(navController = navController, startDestination = "spots") {
                    // Route de la page d'accueil qui liste tous les spots.
                    composable("spots") {
                        SpotList(navController, spots)
                    }
                    // Route qui affiche le détail d'un spot.
                    composable(
                        "spot/{spotId}",
                        arguments = listOf(navArgument("spotId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val spotId = backStackEntry.arguments?.getString("spotId")


                        if (spotId != null) {
                            val spot = runBlocking {
                                SpotsApi.getSpotDetails(spotId)
                            }

                            if (spot != null) {
                                SpotDetails(navController, spot)
                            }
                        } else {
                            // Gérer le cas où l'ID du spot est nul
                            Text("Invalid spot ID")
                        }
                    }
                    // Route pour aller sur la page de création d'un nouveau spot
                    composable(
                        "AddNewSpot"
                    ) {
                        AddNewSpot(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SootheBottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        NavigationBar(
            modifier = modifier
                .align(Alignment.BottomCenter)
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Nouveau spot"
                    )
                },
                selected = true,
                onClick = { navController.navigate("AddNewSpot") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Mes spots"
                    )
                },
                selected = true,
                onClick = { navController.navigate("AddNewSpot") }
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Retour à l'accueil"
                    )
                },
                selected = true,
                onClick = { navController.navigate("spots") }
            )
        }
    }
}

//data class Spot(val id: Int, val name: String, val location: String)

@Composable
fun SpotCard(navController: NavController, content: Spots) {
    // Add padding around our message
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable {
            // Naviguer vers les détails du spot lorsque l'élément est cliqué
            navController.navigate("spot/${content.id}")
        }
    )
    {

        Log.v("spotURL", "Error response: ${content.smallThumbnail}")

        Image(
            painter = rememberAsyncImagePainter(content.smallThumbnail),
            contentDescription = "surfer image",
            modifier = Modifier
                // Set image size to 40 dp
                .size(100.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = content.destination,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(top = 4.dp),
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = content.address,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }


        }
    }

}


@Composable
fun SpotList(navController: NavController, spots: SpotList) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "WaveTracker",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .background(
                    color = Color(0xFF30dcdc),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
                .padding(horizontal = 60.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "SpotList",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn {
            items(spots.spotList) { spot ->
                SpotCard(navController, content = spot)
            }

        }



    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
            SootheBottomNavigation(navController)
    }
    Log.v("spotList", spots.toString())
}

@Composable
fun SpotDetails(navController: NavController, content : SpotDetails) {


    // Add a horizontal space between the image and the column
    Spacer(modifier = Modifier.width(10.dp))

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp),
    ) {


        Text(
                text = content.destination,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

//            Log.d("SpotDetails", "Difficulty Level before StarRating: ${content.fields.difficultyLevel}")
//            StarRating(rating = content.fields.difficultyLevel, onRatingChanged = { /* Gérez les changements de note ici si nécessaire */ })
//            Log.d("SpotDetails", "Difficulty Level after StarRating: ${content.fields.difficultyLevel}")

        Spacer(modifier = Modifier.padding(5.dp))

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {

            val difficultyLevel = content.difficultyLevel

            // boucle pour générer les étoiles
            if (difficultyLevel != null) {
                repeat(difficultyLevel) {
                    Image(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(2.dp)
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = rememberAsyncImagePainter(content.largeThumbnail),
            contentDescription = "surfer image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(all = 4.dp)
                .align(Alignment.CenterHorizontally),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {
            Text(
                text = content.address,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(all = 4.dp)
                .align(Alignment.CenterHorizontally),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {

            Column {

                Text(
                    text = "Surf Break : ${content.surfBreak}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(all = 4.dp)
                .fillMaxWidth(),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp
        ) {

            Column {


                Text(
                    text = "Début de saison : ${content.peakSurfSeasonBegins}",
                    color = Color(0, 200, 0),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(all = 4.dp)
                )


                Text(
                    text = "Fin de saison : ${content.peakSurfSeasonEnds}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(all = 4.dp)
                )
            }
        }


}
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        SootheBottomNavigation(navController)
    }
}