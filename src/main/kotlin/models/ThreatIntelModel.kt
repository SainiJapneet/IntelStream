package org.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ThreatIntelModel(
    val id: Int,
    val type: String,
    val value: String,
    val severity: String
)
@Serializable
data class IOC_ID(
    val ID: Int
)

@Serializable
data class BaseResponse(
    val response: String
)