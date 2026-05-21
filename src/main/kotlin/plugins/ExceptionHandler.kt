package org.example.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.example.models.ErrorResponse

fun Application.configureExceptionHandling() {
    install(StatusPages){
        exception <IllegalArgumentException>{ call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                cause.message?:"Invalid Request"
            )
        }

        exception <NoSuchElementException>{ call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                cause.message?:"Not Found"
            )
        }

        exception <Throwable>{call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Internal Server Error"))
        }
    }
}