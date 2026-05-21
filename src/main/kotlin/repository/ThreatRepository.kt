package org.example.repository

import org.example.models.ThreatIntelModel

object ThreatRepository {
    private val threatIndicators = mutableListOf<ThreatIntelModel>(
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

    fun getAll(): List<ThreatIntelModel> {
        return threatIndicators
    }

    fun addThreat(threatModel: ThreatIntelModel) {
        threatIndicators.add(threatModel)
    }

    fun updateThreat(id: Int, updated: ThreatIntelModel): Boolean {
        val index = threatIndicators.indexOfFirst { it.id == id }
        if (index == -1) {
            return false
        }
        threatIndicators[index] = updated
        return true
    }

    fun deleteThreat(id: Int): Boolean {
        return threatIndicators.removeIf { it.id == id }
    }
}