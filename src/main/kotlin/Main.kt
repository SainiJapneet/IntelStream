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
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.example.models.BaseResponse
import org.example.models.ErrorResponse
import org.example.models.HealthResponse
import org.example.models.IOC_ID
import org.example.models.ThreatIntelModel
import org.example.plugins.configureExceptionHandling
import org.example.routes.threatRoutes
fun main() {
    embeddedServer(Netty, port = 8080) {

        install(ContentNegotiation) {
            json()
        }

        configureExceptionHandling()

        routing {
            get("/"){
                call.respondText { "IntelStream Backend Up and Running..." }
            }

            get("/health"){
                val healthResponse =
                    HealthResponse("UP", "IntelStream", 8080)

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

            threatRoutes()
        }
    }.start(wait = true)
}