package org.example

import kotlinx.serialization.Serializable

@Serializable
data class ThreatIntelModel(
    val id: Int,
    val type: String,
    val value: String,
    val severity: String)

@Serializable
data class HealthResponse(
    val status: String,
    val service: String,
    val port: Int
)

@Serializable
data class ErrorResponse(
    val status: String
)

@Serializable
data class IOC_ID(
    val ID: Int
)

@Serializable
data class BaseResponse(
    val response: String
)