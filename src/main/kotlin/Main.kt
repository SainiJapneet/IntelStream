package org.example

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

val threatIndicators = mutableListOf(

    ThreatIntelModel(
        1,
        "IP",
        "8.8.8.8",
        "LOW"
    ),

    ThreatIntelModel(
        2,
        "DOMAIN",
        "malicious-example.com",
        "HIGH"
    )
)

fun main() {
    embeddedServer(Netty, port = 8080) {

        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/"){
                call.respondText { "IntelStream Backend Up and Running..." }
            }

            get("/health"){
                val healthResponse =
                    HealthResponse("UP", "IntelStream",8080)

                call.respond(
                    healthResponse
                )
            }

            get ("/about"){
                call.respondText {
                    """
                        IntelStream is a ThreatIntelligence Backend platform.
                        
                        Features:
                        Coming soon....
                    """.trimIndent()
                }
            }

            get("/ioc/sample"){
                val indicator = ThreatIntelModel(
                    id = 1,
                    type = "IP",
                    value = "192.168.1.1",
                    severity = "HIGH"
                )

                call.respond(indicator)
            }

            get("/ioc/all") {
                call.respond(threatIndicators)
            }

            get("/ioc/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()

                call.respond(
                    IOC_ID(id?:0)
                )
            }

            get("/search"){
                val type = call.request.queryParameters["type"]

                call.respond(
                    BaseResponse(type?:"")
                )
            }

            get("/unauthorized"){
                call.respond(HttpStatusCode.Unauthorized,
                    ErrorResponse("You are not authorized to access this resource")
                )
            }

            post("/ioc/create"){
                val newIndicator = call.receive<ThreatIntelModel>()
                if (newIndicator.value.isBlank()){
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse("IOC value cannot be blank"))
                    return@post
                }
                threatIndicators.add(newIndicator)
                call.respond(HttpStatusCode.Created, newIndicator)
            }

            put ("/ioc/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse("Invalid IOC ID")
                    )

                    return@put
                }

                val updatedIndicator = call.receive<ThreatIntelModel>()
                val existingIndicatorIndex = threatIndicators.indexOfFirst { it.id == id }

                if (existingIndicatorIndex == -1){
                    call.respond(HttpStatusCode.NotFound,ErrorResponse("ThreatIntelModel not found with ID $id"))

                    return@put
                }

                threatIndicators[existingIndicatorIndex] = updatedIndicator
                call.respond(HttpStatusCode.OK, updatedIndicator)
            }
        }
    }.start(wait = true)
}