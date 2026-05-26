package org.example.repository

import org.example.database.ThreatTable
import org.example.models.ThreatIntelModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object ThreatRepository {

    fun getAll(): List<ThreatIntelModel> {
        return transaction {
            ThreatTable.selectAll().map {
                ThreatIntelModel(
                    id = it[ThreatTable.id],
                    type = it[ThreatTable.type],
                    value = it[ThreatTable.value],
                    severity = it[ThreatTable.severity],
                )
            }
        }
    }

    fun addThreat(indicator: ThreatIntelModel) {
        transaction {
            ThreatTable.insert {

                it[id] = indicator.id
                it[type] = indicator.type
                it[value] = indicator.value
                it[severity] = indicator.severity
            }
        }
    }

    fun updateThreat(id: Int, updated: ThreatIntelModel): Boolean {
        return transaction {
            val updatedRows = ThreatTable.update(
                where = { ThreatTable.id eq id}
            ){
                it[type] = updated.type
                it[value] = updated.value
                it[severity] = updated.severity
            }
            updatedRows > 0
        }
    }

    fun deleteThreat(id: Int): Boolean {
        return transaction {
            val deletedRows = ThreatTable.deleteWhere { ThreatTable.id eq id }
            deletedRows > 0
        }
    }
}