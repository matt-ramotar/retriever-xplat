package com.taaggg.notes.common.storekit.api

import com.taaggg.notes.common.storekit.entities.auth.GoogleAuthResponse
import com.taaggg.notes.common.storekit.entities.auth.GoogleUser
import com.taaggg.notes.common.storekit.entities.user.network.NetworkUser
import com.taaggg.notes.common.storekit.result.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RealNotesApi(private val client: HttpClient) : NotesApi {
    override suspend fun validateToken(token: String): RequestResult<NetworkUser> = try {
        val response = client.post("$ROOT_API_URL/auth/token") {
            setBody(buildMap<String, String> { "token" to token })
            contentType(ContentType.Application.Json)
        }
        RequestResult.Success(response.body())
    } catch (error: Error) {
        RequestResult.Exception(error)
    }

    override suspend fun google(googleUser: GoogleUser): RequestResult<GoogleAuthResponse> = try {
        val response = client.post("${ROOT_API_URL}/auth/google") {
            setBody(googleUser)
            contentType(ContentType.Application.Json)
        }
        val googleAuthResponse = response.body<GoogleAuthResponse>()
        RequestResult.Success(googleAuthResponse)
    } catch (error: Throwable) {
        RequestResult.Exception(error)
    }

    companion object {
        private const val ROOT_API_URL = "https://www.api.notes.taaggg.com/v1"
    }
}