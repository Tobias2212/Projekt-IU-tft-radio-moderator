package de.iu.tftradiomoderator.data.provider


import de.iu.tftradiomoderator.data.error.NetworkException
import de.iu.tftradiomoderator.data.api.ApiService
import de.iu.tftradiomoderator.data.model.SongRequest
import de.iu.tftradiomoderator.ui.Rating
import de.iu.tftradiomoderator.data.model.Moderator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

internal class RadioNetworkProvider {
    private val apiService = createApiService()
    /**
     * Konfiguriert und erstellt die API-Service-Instanz.
     */

    @Throws(NetworkException::class)
    private fun createApiService(): ApiService {
        val baseUrl = "https://tft-radio.resources"
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
            throw NetworkException()
        }
    }
    /**
     * Song Requests vom Server abrufen.
     */
    @Throws(NetworkException::class)
    suspend fun getSongRequests(): List<SongRequest> {
        val response = apiService.getSongRequests()
        return response.body() ?: throw NetworkException()
    }

    /**
     * Moderatorinformationen abrufen.
     */
    @Throws(NetworkException::class)
    suspend fun getModerator(): Moderator  {
        println("Moderator: ${apiService.getModerator().body()}")
        val response = apiService.getModerator()
        return response.body() ?: throw NetworkException()
    }

    /**
     * Bewertungen des Moderators abrufen.
     */
    @Throws(NetworkException::class)
    suspend fun getRatings(): List<Rating> {
        val response = apiService.getRatings()
        return response.body() ?: throw NetworkException()
    }
    }

