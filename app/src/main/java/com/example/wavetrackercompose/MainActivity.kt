package com.example.wavetrackercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wavetrackercompose.ui.theme.WaveTrackerComposeTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveTrackerComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "spotlist") {
                    composable("spotlist") {
                        SpotList(
                            detailsButtonClick = { navController.navigate("spotdetails") },
                            userButtonClick = { navController.navigate("user") }) }
                    composable("spotdetails") { SpotDetails(/*...*/) }
                    composable("user") { User(/*...*/) }
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

data class Spot(val name: String, val location: String)

@Composable
fun SpotCard(content: Spot) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.surfeur_grande_generation_blue_ocean_wave_ai),
            contentDescription = "surfer image",
            modifier = Modifier
                // Set image size to 40 dp
                .size(50.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = content.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(all = 4.dp),
                tonalElevation = 1.dp,
                shadowElevation = 1.dp
            ) {
                Text(
                    text = content.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}

@Preview
@Composable
fun PreviewSpotCard() {
    SpotCard(
        content = Spot("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
    )
}


@Composable
fun Spot(spots: List<Spot>) {
    LazyColumn {
        items(spots) { spot ->
            SpotCard(spot)
        }
    }
}


@Composable
fun SpotList(detailsButtonClick: () -> Unit, userButtonClick: () -> Unit) {
    Column {
        Button(onClick = detailsButtonClick) {
            Text(text = "Détails")
        }

        Button(onClick = userButtonClick) {
            Text(text = "User")
        }
    }

}

@Composable
fun SpotDetails() {
    Text(text = "Details")
}

@Composable
fun User() {
    Text(text = "User")
}

