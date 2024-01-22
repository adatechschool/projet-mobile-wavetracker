package com.wavetracker.plugins

import com.wavetracker.SpotList
import com.wavetracker.loadSpotListFromJson
import com.wavetracker.loadSpotFromJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }

        // Route pour récupérer la liste des spots
        get("/spots") {
            // Logique pour récupérer la liste des spots
            val spotsObject = loadSpotListFromJson()
            call.respond(spotsObject)
        }

        // Route pour récupérer un spot par son ID
        get("/spots/{id}") {
            val spotId = call.parameters["id"]
            if (spotId != null) {
                val spot = loadSpotFromJson(spotId)

                if (spot != null) {
                    call.respond(spot)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Spot with ID $spotId not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid spot ID")
            }
        }

    }
}
