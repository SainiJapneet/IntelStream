package org.example.database

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val dbUrl = System.getenv("DB_URL_SB")
        val dbUser = System.getenv("DB_USER_SB")
        val dbPassword = System.getenv("DB_PWD_SB")

        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        println("Connected to Supabase PostgreSQL")
    }
}