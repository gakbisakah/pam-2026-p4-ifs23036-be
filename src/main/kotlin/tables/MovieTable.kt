package org.delcom.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime

object MovieTable : UUIDTable("movies") {
    val title = varchar("title", 255)
    val director = varchar("director", 255)
    val releaseYear = integer("release_year")
    val genre = varchar("genre", 100)
    val rating = double("rating").default(0.0)
    val description = text("description").nullable()
    val posterPath = varchar("poster_path", 255).nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}
