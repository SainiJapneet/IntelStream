package org.example

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

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

                val indicators = listOf(

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
                    ),

                    ThreatIntelModel(
                        3,
                        "HASH",
                        "a94a8fe5ccb19ba61c4",
                        "CRITICAL"
                    )
                )

                call.respond(indicators)
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


        }
    }.start(wait = true)
}