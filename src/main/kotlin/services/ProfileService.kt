package org.delcom.services

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.delcom.data.DataResponse
import java.io.File

class ProfileService {
    // Mengambil semua data tumbuhan
    suspend fun getProfile(call: ApplicationCall) {
        val response = DataResponse(
            "success",
            "Berhasil mengambil profile pengembang",
            mapOf(
                Pair("username", "Christian Johannes Hutahaean"),
                Pair("nama", "Christian Johannes Hutahaean"),
                Pair("tentang", "Saya adalah seorang developer yang tertarik pada mobile development, backend API, dan berbagai teknologi pengembangan aplikasi. Senang belajar hal baru dan membangun aplikasi yang berguna."),
            )
        )
        call.respond(response)
    }

    // Mengambil photo profile
    suspend fun getProfilePhoto(call: ApplicationCall) {
        val basePath = System.getProperty("user.dir")
        val file = File(basePath, "app/src/image/me.png")

        if (!file.exists()) {
            println("File tidak ditemukan di: ${file.absolutePath}")
            return call.respond(HttpStatusCode.NotFound)
        }

        call.respondFile(file)
    }
}