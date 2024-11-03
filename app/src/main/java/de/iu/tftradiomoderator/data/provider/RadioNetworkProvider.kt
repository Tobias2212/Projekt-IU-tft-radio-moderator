package de.iu.tftradiomoderator.data.provider

import de.iu.tftradiomoderator.data.api.ApiService
import de.iu.tftradiomoderator.data.error.NetworkException
import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.ui.Rating
import de.iu.tftradiomoderator.data.objects.Moderator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class RadioNetworkProvider {

    private val apiService = createApiService()

    /**
     * Konfiguriert und erstellt die API-Service-Instanz.
     */
    @Throws(NetworkException::class)
    private fun createApiService(): ApiService {
        val baseUrl = "http://tft-radio.resources"
        return try {
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder().build()
                    chain.proceed(request)
                }
                .build()

            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        } catch (e: Exception) {
            println("Error creating API service: ${e.localizedMessage}")
            throw NetworkException("Error creating API service: ${e.localizedMessage}")
        }
    }

    /**
     * Song Requests vom Server abrufen.
     */
    suspend fun getSongRequests(): List<SongRequest> {
        val response = apiService.getSongRequests()
        if (response.isSuccessful) {

            return response.body() ?: throw NetworkException("Keine Daten erhalten")
        } else {
            println("Fehler beim Abrufen der Song Request-Liste: ${response.errorBody()?.string()}")
            throw NetworkException("Fehler beim Abrufen der Song Request-Liste")
        }
    }


    /**
     * Moderatorinformationen abrufen.
     */
    suspend fun getModerator(): Moderator  {
        println("Moderator: ${apiService.getModerator().body()}")
        val response = apiService.getModerator()
        if (response.isSuccessful) {     println("Antwort erhalten Moderator : ${response.body()}")
            return response.body() ?: throw NetworkException("Keine Daten erhalten")
        } else {
            println("Fehler beim Abrufen der Moderators: ${response.errorBody()?.string()}")
            throw NetworkException("Fehler beim Abrufen der Rating-Liste")
        }

    }


    /**
     * Bewertungen des Moderators abrufen.
     */
    suspend fun getRatings(): List<Rating> {
        val response = apiService.getRatings()
        if (response.isSuccessful) {     println("Antwort erhalten Rating: ${response.body()}")
            return response.body() ?: throw NetworkException("Keine Daten erhalten")
        } else { println("Fehler beim Abrufen der Rating-Liste: ${response.errorBody()?.string()}")
            throw NetworkException("Fehler beim Abrufen der Rating-Liste")
        }
    }



    }

