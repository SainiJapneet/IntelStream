package org.example.service

import org.example.models.ThreatIntelModel
import org.example.repository.ThreatRepository

object ThreatService {
    fun getALlIndicators(): List<ThreatIntelModel>{
        return ThreatRepository.getAll()
    }

    fun createIndicator(indicator: ThreatIntelModel){
        if (indicator.value.isBlank()){
            throw IllegalArgumentException("IOC value cannot be blank")
        }
        ThreatRepository.addThreat(indicator)
    }

    fun updateIndicator(id: Int, indicator: ThreatIntelModel): Boolean{
        return ThreatRepository.updateThreat(id, indicator)
    }

    fun deleteIndicator(id: Int): Boolean{
        return ThreatRepository.deleteThreat(id)
    }
}