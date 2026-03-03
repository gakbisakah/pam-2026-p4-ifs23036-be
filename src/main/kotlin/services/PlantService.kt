package org.delcom.services

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import org.delcom.entities.Plant
import org.delcom.repositories.IPlantRepository
import java.io.File

class PlantService(private val repository: IPlantRepository) {
    suspend fun getAllPlants(call: ApplicationCall) {
        // Menyesuaikan dengan IPlantRepository: memanggil getPlants("")
        val plants = repository.getPlants("") 
        call.respond(plants)
    }

    suspend fun createPlant(call: ApplicationCall) {
        val plant = call.receive<Plant>()
        // Menyesuaikan dengan IPlantRepository: addPlant mengembalikan String
        repository.addPlant(plant)
        call.respond(HttpStatusCode.Created, "Plant created successfully")
    }

    suspend fun getPlantById(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        val plant = repository.getPlantById(id)
        if (plant != null) call.respond(plant) else call.respond(HttpStatusCode.NotFound)
    }

    suspend fun updatePlant(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        val plant = call.receive<Plant>()
        val updated = repository.updatePlant(id, plant)
        if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
    }

    suspend fun deletePlant(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        // Menyesuaikan dengan IPlantRepository: memanggil removePlant(id)
        val deleted = repository.removePlant(id)
        if (deleted) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
    }

    suspend fun getPlantImage(call: ApplicationCall) {
        val id = call.parameters["id"] ?: return call.respond(HttpStatusCode.BadRequest, "Missing id")
        val plant = repository.getPlantById(id)
        if (plant != null && plant.pathGambar.isNotEmpty()) {
            val file = File(plant.pathGambar)
            if (file.exists()) {
                call.respondFile(file)
            } else {
                call.respond(HttpStatusCode.NotFound, "Image file not found")
            }
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}
