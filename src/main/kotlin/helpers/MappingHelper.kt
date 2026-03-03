package org.delcom.helpers

import kotlinx.coroutines.Dispatchers
import org.delcom.dao.PlantDAO
import org.delcom.dao.MovieDAO
import org.delcom.entities.Plant
import org.delcom.entities.Movie
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: PlantDAO) = Plant(
    id = dao.id.value.toString(),
    nama = dao.nama,
    pathGambar = dao.pathGambar,
    deskripsi = dao.deskripsi,
    manfaat = dao.manfaat,
    efekSamping = dao.efekSamping,
    createdAt = dao.createdAt, // Sudah Instant (dari timestamp)
    updatedAt = dao.updatedAt  // Sudah Instant (dari timestamp)
)

fun daoToModel(dao: MovieDAO) = Movie(
    id = dao.id.value.toString(),
    title = dao.title,
    director = dao.director,
    releaseYear = dao.releaseYear,
    genre = dao.genre,
    rating = dao.rating,
    description = dao.description ?: "",
    posterPath = dao.posterPath ?: "",
    // Konversi dari kotlinx.datetime.LocalDateTime ke Instant
    createdAt = dao.createdAt.toInstant(TimeZone.UTC),
    updatedAt = dao.updatedAt.toInstant(TimeZone.UTC)
)