package org.delcom.repositories

import org.delcom.dao.MovieDAO
import org.delcom.entities.Movie
import org.delcom.helpers.daoToModel
import org.delcom.helpers.suspendTransaction
import java.util.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MovieRepository : IMovieRepository {

    override suspend fun getAllMovies(): List<Movie> = suspendTransaction {
        MovieDAO.all().map(::daoToModel)
    }

    override suspend fun getMovieById(id: String): Movie? = suspendTransaction {
        try {
            MovieDAO.findById(UUID.fromString(id))?.let(::daoToModel)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addMovie(movie: Movie): Movie = suspendTransaction {
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)

        val newDao = MovieDAO.new(UUID.fromString(movie.id)) {
            title = movie.title
            director = movie.director
            releaseYear = movie.releaseYear
            genre = movie.genre
            rating = movie.rating
            description = movie.description
            posterPath = movie.posterPath

            createdAt = now
            updatedAt = now
        }

        daoToModel(newDao)
    }

    override suspend fun updateMovie(id: String, movie: Movie): Boolean = suspendTransaction {
        try {
            val uuid = UUID.fromString(id)
            val found = MovieDAO.findById(uuid)
                ?: return@suspendTransaction false

            found.apply {
                title = movie.title
                director = movie.director
                releaseYear = movie.releaseYear
                genre = movie.genre
                rating = movie.rating
                description = movie.description
                // Hanya update posterPath jika tidak null/kosong dari frontend
                if (!movie.posterPath.isNullOrEmpty()) {
                    posterPath = movie.posterPath
                }

                updatedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteMovie(id: String): Boolean = suspendTransaction {
        try {
            val found = MovieDAO.findById(UUID.fromString(id))
                ?: return@suspendTransaction false

            found.delete()
            true
        } catch (e: Exception) {
            false
        }
    }
}
