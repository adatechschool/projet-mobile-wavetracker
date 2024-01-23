// Application.kt

package com.wavetracker

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wavetracker.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File
import java.util.*

// Fonction principale qui démarre le serveur Ktor
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

// Fonction de configuration du module Ktor
fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
}

// Classe représentant les données de la liste des spots avec les informations nécessaires
data class SpotList(
    val id: String,
    val destination: String,
    val address: String,
    val surfBreak: String,
    val photos: String,
)

// Classe représentant les données d'un spot avec les informations nécessaires
data class Spot(
    val id: String,
    val destination: String,
    val address: String,
    val peakSurfSeasonBegins: String,
    val peakSurfSeasonEnds: String,
    val difficultyLevel: Int,
    val surfBreak: String,
    val photos : String,
    val geocode: String,
)

// Fonction pour charger les données des spots à partir du fichier JSON
fun loadSpotListFromJson(): SpotsObject {
    // Nom du fichier JSON
    val jsonFileName = "spots.json"

    return try {
        // Charger le contenu du fichier JSON
        val jsonString = object {}.javaClass.classLoader.getResourceAsStream(jsonFileName)?.bufferedReader().use { it?.readText() }

        jsonString?.let {
            // Mapper JSON à la classe SpotData
            val objectMapper = jacksonObjectMapper()
            val spotData: SpotData = objectMapper.readValue(it)

            //println("SpotData: ${spotData}")

            // Mapper chaque enregistrement SpotData à la classe SpotList
            val spotsList = spotData.records.map { record ->
                SpotList(
                    id = record.id,
                    destination = record.fields.destination,
                    address = record.fields.address,
                    surfBreak = record.fields.surfBreak,
                    photos = record.fields.photos,
                )
            }

            // Créer un objet encapsulant la liste
            val spotsObject = SpotsObject(spotsList)

            return spotsObject
        } ?: SpotsObject(emptyList())  // Retourner un objet SpotsObject vide si le fichier JSON est vide ou inexistant
    } catch (e: Exception) {
        println("Error loading spots: ${e.message}")
        return SpotsObject(emptyList())  // Retourner un objet SpotsObject vide en cas d'erreur lors du chargement du fichier JSON
    }
}


// Fonction pour charger les données d'un spot à partir du fichier JSON avec la structure Spot
fun loadSpotFromJson(spotId: String): Spot? {
    // Nom du fichier JSON
    val jsonFileName = "spots.json"

    return try {
        // Charger le contenu du fichier JSON
        val jsonString = object {}.javaClass.classLoader.getResourceAsStream(jsonFileName)?.bufferedReader().use { it?.readText() }

        jsonString?.let {
            // Mapper JSON à la classe SpotData
            val objectMapper = jacksonObjectMapper()
            val spotData: SpotData = objectMapper.readValue(it)

            // Rechercher le spot par ID
            val spotRecord = spotData.records.find { it.id == spotId }

            // Mapper le spot à la classe Spot
            return spotRecord?.let { record ->
                Spot(
                    id = record.id,
                    destination = record.fields.destination,
                    address = record.fields.address,
                    peakSurfSeasonBegins = record.fields.peakSurfSeasonBegins,
                    peakSurfSeasonEnds = record.fields.peakSurfSeasonEnds,
                    difficultyLevel = record.fields.difficultyLevel,
                    surfBreak = record.fields.surfBreak,
                    photos = record.fields.photos,
                    geocode = record.fields.geocode,
                )
            }
        }
        // Retourner null si le fichier JSON est vide ou inexistant
        null
    } catch (e: Exception) {
        println("Error loading spot: ${e.message}")
        null  // Retourner null en cas d'erreur lors du chargement du fichier JSON
    }
}

data class Spots(
    val records: MutableList<Record>
)


fun addSpot(newSpot: NewSpot) {
    // Charger le fichier JSON existant
    val jsonString = File("C:/Android/AndroidStudioProjects/projet-mobile-wavetracker/ktor-wavetracker/src/main/resources/spots.json").readText()
    println("Existing JSON content: $jsonString")

    // Mapper le JSON à la classe Spots
    val spots = jacksonObjectMapper().readValue<Spots>(jsonString)

    // Ajouter le nouveau spot à la liste existante
    spots.records.add(
        Record(
            id = UUID.randomUUID().toString(),
            createdTime = "NOUVELLE_DATE_CREATION",
            fields = Fields(
                geocode = newSpot.geocode,
                photos = newSpot.photos,
                peakSurfSeasonBegins = newSpot.peakSurfSeasonBegins,
                destinationStateCountry = newSpot.destinationStateCountry,
                peakSurfSeasonEnds = newSpot.peakSurfSeasonEnds,
                difficultyLevel = newSpot.difficultyLevel,
                destination = newSpot.destination,
                surfBreak = newSpot.surfBreak,
                address = newSpot.address
            )
        )
    )

    // Afficher la liste mise à jour
    println("Updated spot list: $spots")

    // Convertir la liste mise à jour en JSON et l'écrire dans le fichier
    val updatedJson = jacksonObjectMapper().writeValueAsString(spots)
    File("spots.json").writeText(updatedJson)
}




