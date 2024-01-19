//MainActivity.kt

package com.example.wavetrackercompose

import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.wavetrackercompose.model.Record
import com.example.wavetrackercompose.model.ResponseModel
import com.example.wavetrackercompose.network.SpotsApi
import kotlinx.coroutines.runBlocking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.wavetrackercompose.navigation_bottomnavbar.screens.AddNewSpot
import com.example.wavetrackercompose.navigation_bottomnavbar.screens.BackToHomeButton
import com.example.wavetrackercompose.navigation_bottomnavbar.screens.navigation.Screens


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveTrackerComposeTheme {
                val navController = rememberNavController()
                val spots = runBlocking {
                    SpotsApi.getSpots()
                }
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
                        val spot = spots.records.find { it.id == spotId }

                        if (!spots.records.isNullOrEmpty() && spot != null) {
                            SpotDetails(navController, spot)
                        } else {
                            // Gérer le cas où le spot n'est pas trouvé ou la liste de spots est vide
                            Text("Spot not found")
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

data class Spot(val id: Int, val name: String, val location: String)

@Composable
fun SpotCard(navController: NavController, content: Record) {
    // Add padding around our message
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .clickable {
            // Naviguer vers les détails du spot lorsque l'élément est cliqué
            navController.navigate("spot/${content.id}")
        }
    )
    {
        AsyncImage(
            model = content.fields.Photos.first().url,
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
                text = content.fields.Destination,
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
                    text = content.fields.destinationStateCountry,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
            }

        }
    }

}


@Composable
fun SpotList(navController: NavController, spots: ResponseModel) {
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
            items(spots.records) { spot ->
                SpotCard(navController, content = spot)
            }
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        // Bouton pour ajouter un nouveau spot
        Button(onClick = {
            navController.navigate("AddNewSpot")
        }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Ajouter")
        }
    }
    Log.v("spotList", spots.toString())
}


//@Composable
//fun SpotList(detailsButtonClick: () -> Unit, userButtonClick: () -> Unit) {
// Column {
//   Button(onClick = detailsButtonClick) {
//       Text(text = "Détails")
//  }
//
//  Button(onClick = userButtonClick) {
//      Text(text = "User")
//    }
// }
//}

@Composable
fun SpotDetails(navController: NavController, content: Record) {


    // Add a horizontal space between the image and the column
    Spacer(modifier = Modifier.width(10.dp))

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp),
    ) {


        Text(
            text = content.fields.Destination,
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
/*                Text(
                    text = "${content.fields.difficultyLevel}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                )*/

            val difficultyLevel = content.fields.difficultyLevel ?: 0

            // boucle pour générer les étoiles
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


        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = content.fields.Photos.first().thumbnails.large.url,
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
                text = content.fields.destinationStateCountry,
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
                    text = "Surf Break : ${content.fields.surfBreak.first()}",
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
                    text = "Début de saison : ${content.fields.peakSurfSeasonBegins}",
                    color = Color(0, 200, 0),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(all = 4.dp)
                )


                Text(
                    text = "Fin de saison : ${content.fields.peakSurfSeasonEnds}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(all = 4.dp)
                )
            }
        }
        // Bouton de retour à la page d'accueil
        BackToHomeButton(navController)

}}