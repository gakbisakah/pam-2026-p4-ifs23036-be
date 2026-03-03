package org.delcom.services

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.delcom.entities.Movie
import org.delcom.repositories.IMovieRepository
import java.io.File
import java.util.*

class MovieService(private val repository: IMovieRepository) {
    suspend fun getAllMovies(call: ApplicationCall) {
        val movies = repository.getAllMovies()
        call.respond(movies)
    }

    suspend fun getMovieById(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        val movie = repository.getMovieById(id)
        if (movie != null) {
            call.respond(movie)
        } else {
            call.respond(HttpStatusCode.NotFound, "Movie not found")
        }
    }

    suspend fun createMovie(call: ApplicationCall) {
        var title = ""
        var director = ""
        var releaseYear = 0
        var genre = ""
        var rating = 0.0
        var description = ""
        var posterPath: String? = null

        val multipartData = call.receiveMultipart()
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "title" -> title = part.value
                        "director" -> director = part.value
                        "releaseYear" -> releaseYear = part.value.toIntOrNull() ?: 0
                        "genre" -> genre = part.value
                        "rating" -> rating = part.value.toDoubleOrNull() ?: 0.0
                        "description" -> description = part.value
                    }
                }
                is PartData.FileItem -> {
                    val ext = part.originalFileName?.substringAfterLast('.', "") ?: ""
                    val fileName = "${UUID.randomUUID()}.$ext"
                    val file = File("uploads/movies/$fileName")
                    file.parentFile.mkdirs()
                    part.provider().copyAndClose(file.writeChannel())
                    posterPath = "uploads/movies/$fileName"
                }
                else -> {}
            }
            part.dispose()
        }

        val movie = Movie(
            title = title,
            director = director,
            releaseYear = releaseYear,
            genre = genre,
            rating = rating,
            description = description,
            posterPath = posterPath ?: ""
        )
        
        val createdMovie = repository.addMovie(movie)
        call.respond(HttpStatusCode.Created, createdMovie)
    }

    suspend fun getMovieImage(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest)
        val movie = repository.getMovieById(id) ?: return call.respond(HttpStatusCode.NotFound)
        val path = movie.posterPath
        if (path.isEmpty()) return call.respond(HttpStatusCode.NotFound, "No poster for this movie")
        
        val file = File(path)
        if (file.exists()) call.respondFile(file)
        else call.respond(HttpStatusCode.NotFound)
    }

    suspend fun updateMovie(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        try {
            val movie = call.receive<Movie>() 
            val updated = repository.updateMovie(id, movie)
            if (updated) {
                // Mengirim JSON, bukan plain text
                call.respond(HttpStatusCode.OK, mapOf("message" to "Movie updated successfully"))
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("message" to "Movie not found"))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Invalid data format"))
        }
    }

    suspend fun deleteMovie(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        val movie = repository.getMovieById(id)
        val deleted = repository.deleteMovie(id)
        if (deleted) {
            movie?.posterPath?.let { 
                if (it.isNotEmpty()) {
                    val file = File(it)
                    if (file.exists()) file.delete()
                }
            }
            // Mengirim JSON
            call.respond(HttpStatusCode.OK, mapOf("message" to "Movie deleted successfully"))
        } else {
            call.respond(HttpStatusCode.NotFound, mapOf("message" to "Movie not found"))
        }
    }
}
