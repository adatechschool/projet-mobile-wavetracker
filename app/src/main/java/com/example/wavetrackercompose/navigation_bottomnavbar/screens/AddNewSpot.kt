package com.example.wavetrackercompose.navigation_bottomnavbar.screens

import android.app.Activity
import android.content.Intent
import android.text.style.BackgroundColorSpan
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
/*import androidx.compose.foundation.layout.BoxScopeInstance.align*/
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
/*import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.FlowColumnScopeInstance.align*/
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wavetrackercompose.SootheBottomNavigation


// Fonction qui permet de naviguer vers l'écran d'accueil
// (navigateUp() renvoie vers l'écran précédent).
@Composable
fun BackToHomeButton(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text(text = "Retour à l'accueil")
        }
    }
}


@Composable
fun AddNewSpot(navController: NavHostController) {
    var surfSpotName by rememberSaveable { mutableStateOf("") }
    var locationName by rememberSaveable { mutableStateOf("") }
    var seasonStart by rememberSaveable { mutableStateOf("") }
    var seasonEnds by rememberSaveable { mutableStateOf("") }
    var selectedSurfBreak by rememberSaveable { mutableStateOf(0) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Traiter l''image sélectionnée.
            val imageUri = result.data?.data
        }
    }

    // Contenu de la page
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ajouter un nouveau spot de surf",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.padding(5.dp))
        // Champ de saisie pour le nom du spot
        Text(
            text = "Nom du spot: "
        )
        TextField(
            modifier = Modifier.fillMaxHeight(),
            value = surfSpotName,
            onValueChange = { surfSpotName = it },
            placeholder = { Text(text = "ex: Pointe de Lizay") }
        )

        Spacer(modifier = Modifier.padding(5.dp))
        // Champ de saisie pour la localisation.
        Text(
            text = "Localisation: "
        )
        TextField(
            modifier = Modifier.fillMaxHeight(),
            value = locationName,
            onValueChange = { locationName = it },
            placeholder = { Text(text = "ex: Le Cap, France") }
        )

        Spacer(modifier = Modifier.padding(5.dp))

        // Champ de saisie pour le début de la saison.
        Text(
            text = "Début de saison: "
        )
        TextField(
            modifier = Modifier.fillMaxHeight(),
            value = seasonStart,
            onValueChange = { seasonStart = it },
            placeholder = { Text(text = "ex: 2024-08-23") }
        )

        Spacer(modifier = Modifier.padding(5.dp))

        // Champ de saisie pour la fin de la saison.
        Text(
            text = "Fin de saison: "
        )
        TextField(
            modifier = Modifier.fillMaxHeight(),
            value = seasonEnds,
            onValueChange = { seasonEnds = it },
            placeholder = { Text(text = "ex: 2024-10-17") }
        )
        Spacer(modifier = Modifier.padding(5.dp))

        Text("Type de surf break :")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy((-20).dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Checkbox(
                    checked = selectedSurfBreak == 1,
                    onCheckedChange = { selectedSurfBreak = if (it) 1 else 0 },
                )
                Text("Beach break")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Checkbox(
                    checked = selectedSurfBreak == 2,
                    onCheckedChange = { selectedSurfBreak = if (it) 2 else 0 },
                )
                Text("Reef break")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Checkbox(
                    checked = selectedSurfBreak == 3,
                    onCheckedChange = { selectedSurfBreak = if (it) 3 else 0 },
                )
                Text("Point break")
            }
        }


        Spacer(modifier = Modifier.padding(4.dp))
        // Bouton pour uploader une image.
        Button(onClick = {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imagePicker.launch(intent)
        }) {
            Text(
                text = "Chargez une image"
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        // Bouton de validation du formulaire
        Button(onClick = {
            navController.navigateUp()},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor("#00561b")),
                contentColor = Color.White),
            modifier = Modifier
                .width(170.dp)
                .padding(bottom = 80.dp)
        ) {
            Text(text = "Valider")
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        SootheBottomNavigation(navController)
    }

}
