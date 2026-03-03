package org.delcom.module

import org.delcom.repositories.IPlantRepository
import org.delcom.repositories.PlantRepository
import org.delcom.repositories.IMovieRepository
import org.delcom.repositories.MovieRepository
import org.delcom.services.PlantService
import org.delcom.services.ProfileService
import org.delcom.services.MovieService
import org.koin.dsl.module

val appModule = module {

    // =========================
    // Plant
    // =========================
    single<IPlantRepository> {
        PlantRepository()
    }

    single {
        PlantService(get())
    }

    // =========================
    // Movie (NEW)
    // =========================
    single<IMovieRepository> {
        MovieRepository()
    }

    single {
        MovieService(get())
    }

    // =========================
    // Profile
    // =========================
    single {
        ProfileService()
    }
}
