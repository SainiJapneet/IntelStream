package org.example.database

import org.jetbrains.exposed.sql.Table

object ThreatTable: Table("threat_indicators") {
    val id = integer("id")

    val type = varchar("type", 50)

    val value = text("value")

    val severity = varchar("severity", 50)

    override val primaryKey = PrimaryKey(id)
}