package org.delcom.repositories

import org.delcom.entities.Movie

interface IMovieRepository {
    suspend fun getAllMovies(): List<Movie>
    suspend fun getMovieById(id: String): Movie?
    suspend fun addMovie(movie: Movie): Movie
    suspend fun updateMovie(id: String, movie: Movie): Boolean
    suspend fun deleteMovie(id: String): Boolean
}
