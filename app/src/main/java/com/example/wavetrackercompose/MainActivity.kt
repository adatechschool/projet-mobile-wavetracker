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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveTrackerComposeTheme {
                val navController = rememberNavController()
                val spots = runBlocking {
                    SpotsApi.getSpots()
                }
                NavHost(navController = navController, startDestination = "spots") {
                    composable("spots") {
                        SpotList(navController, spots)
                    }
                    composable(
                        "spot/{spotId}",
                        arguments = listOf(navArgument("spotId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val spotId = backStackEntry.arguments?.getString("spotId")
                        val spot = spots.records.find { it.id == spotId }

                        if (!spots.records.isNullOrEmpty() && spot != null) {
                            SpotDetails(spot)
                        } else {
                            // Gérer le cas où le spot n'est pas trouvé ou la liste de spots est vide
                            Text("Spot not found")
                        }
                    }
//                    composable("user") { User(/*...*/) }
                    /*...*/
                }

                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Spot(Spots.spotsSample)
//
//                }
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
                    .padding(all = 4.dp)
                    .fillMaxWidth(),
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = content.fields.destinationStateCountry,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}


@Composable
fun SpotList(navController: NavController, spots: ResponseModel) {
    LazyColumn {
        items(spots.records) { spot ->
            SpotCard(navController, content = spot)
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
fun SpotDetails(content: Record) {




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
                style = MaterialTheme.typography.titleMedium,
            )

//            Log.d("SpotDetails", "Difficulty Level before StarRating: ${content.fields.difficultyLevel}")
//            StarRating(rating = content.fields.difficultyLevel, onRatingChanged = { /* Gérez les changements de note ici si nécessaire */ })
//            Log.d("SpotDetails", "Difficulty Level after StarRating: ${content.fields.difficultyLevel}")

            Row {
                Text(
                    text = "${content.fields.difficultyLevel}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                )

                Image(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp)
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = content.fields.Photos.first().thumbnails.large.url,
                contentDescription = "surfer image",
                //contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(all = 4.dp)
                    .fillMaxWidth(),
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = content.fields.destinationStateCountry,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Début de saison : ${content.fields.peakSurfSeasonBegins}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = "Fin de saison : ${content.fields.peakSurfSeasonEnds}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = "Surf Break : ${content.fields.surfBreak.first()}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )


        }

}

//@Composable
//fun User() {
//    Text(text = "User")
//}

