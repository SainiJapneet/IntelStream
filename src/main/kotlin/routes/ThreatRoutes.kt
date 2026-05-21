package org.example.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.example.models.ErrorResponse
import org.example.models.ThreatIntelModel
import org.example.service.ThreatService

fun Route.threatRoutes(){
    route("/ioc"){

        get ("/all"){
            call.respond(ThreatService.getALlIndicators())
        }

        post ("/create"){
                val indicator = call.receive<ThreatIntelModel>()
                ThreatService.createIndicator(indicator)
                call.respond(HttpStatusCode.Created, indicator)
        }
        put ("/{id}"){
           val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid IOC ID"))
                return@put
            }
            val updateIndicator = call.receive<ThreatIntelModel>()
            val updated = ThreatService.updateIndicator(updateIndicator.id, updateIndicator)

            if (!updated){
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("IOC not found"))
                return@put
            }
            call.respond(HttpStatusCode.OK, updated)
        }
        delete ("/{id}"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Invalid ID"))
                return@delete
            }
            val deleted = ThreatService.deleteIndicator(id)

            if (!deleted){
                call.respond(HttpStatusCode.BadRequest, ErrorResponse("Threat ID not found"))
                return@delete
            }

            call.respond(HttpStatusCode.OK, ErrorResponse("Threat ID is $deleted"))

        }
    }
}