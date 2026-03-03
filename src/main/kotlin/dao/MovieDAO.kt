package org.delcom.dao

import org.delcom.tables.MovieTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class MovieDAO(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, MovieDAO>(MovieTable)

    var title by MovieTable.title
    var director by MovieTable.director
    var releaseYear by MovieTable.releaseYear
    var genre by MovieTable.genre
    var rating by MovieTable.rating
    var description by MovieTable.description
    var posterPath by MovieTable.posterPath
    var createdAt by MovieTable.createdAt
    var updatedAt by MovieTable.updatedAt
}
