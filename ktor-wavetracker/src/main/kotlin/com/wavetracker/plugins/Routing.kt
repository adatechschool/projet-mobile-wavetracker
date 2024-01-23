package com.wavetracker.plugins

import com.wavetracker.NewSpot
import com.wavetracker.addSpot
import com.wavetracker.loadSpotListFromJson
import com.wavetracker.loadSpotFromJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.receive
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



        post("/spots") {
            val newSpot = call.receive<NewSpot>() // Utilisez la fonction receive pour récupérer les données du corps de la requête

            if (newSpot != null) {
                val spotsObject = addSpot(newSpot)

                if (spotsObject != null) {
                    call.respond(spotsObject)
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Spot request is bad, do it again, better, BIATCH"
                    )
                }

                // Logique pour créer un nouveau spot en utilisant les données de newSpot
            }else{
                call.respond(HttpStatusCode.Created, "Spot created successfully")
            }
        }


    }
}
