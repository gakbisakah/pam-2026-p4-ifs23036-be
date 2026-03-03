package org.delcom.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Movie(
    var id: String = UUID.randomUUID().toString(),
    var title: String,
    var director: String,
    var releaseYear: Int,
    var genre: String,
    var rating: Double,
    var description: String = "",
    var posterPath: String = "",
    val createdAt: Instant = Clock.System.now(),
    var updatedAt: Instant = Clock.System.now(),
)
