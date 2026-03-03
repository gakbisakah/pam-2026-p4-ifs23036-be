package org.delcom.repositories

import org.delcom.dao.PlantDAO
import org.delcom.entities.Plant
import org.delcom.helpers.daoToModel
import org.delcom.helpers.suspendTransaction
import org.delcom.tables.PlantTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class PlantRepository : IPlantRepository {
    override suspend fun getPlants(search: String): List<Plant> = suspendTransaction {
        PlantDAO.find { PlantTable.nama like "%$search%" }.map(::daoToModel)
    }

    override suspend fun getPlantById(id: String): Plant? = suspendTransaction {
        PlantDAO.findById(UUID.fromString(id))?.let(::daoToModel)
    }

    override suspend fun getPlantByName(name: String): Plant? = suspendTransaction {
        PlantDAO.find { PlantTable.nama eq name }.firstOrNull()?.let(::daoToModel)
    }

    override suspend fun addPlant(plant: Plant): String = suspendTransaction {
        PlantDAO.new {
            nama = plant.nama
            pathGambar = plant.pathGambar
            deskripsi = plant.deskripsi
            manfaat = plant.manfaat
            efekSamping = plant.efekSamping
            createdAt = plant.createdAt
            updatedAt = plant.updatedAt
        }.id.value.toString()
    }

    override suspend fun updatePlant(id: String, newPlant: Plant): Boolean = suspendTransaction {
        val plantDao = PlantDAO.findById(UUID.fromString(id)) ?: return@suspendTransaction false
        plantDao.apply {
            nama = newPlant.nama
            pathGambar = newPlant.pathGambar
            deskripsi = newPlant.deskripsi
            manfaat = newPlant.manfaat
            efekSamping = newPlant.efekSamping
            updatedAt = newPlant.updatedAt
        }
        true
    }

    override suspend fun removePlant(id: String): Boolean = suspendTransaction {
        PlantTable.deleteWhere { PlantTable.id eq UUID.fromString(id) } > 0
    }
}
