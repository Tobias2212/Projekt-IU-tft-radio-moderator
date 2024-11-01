package de.iu.tftradiomoderator.data.provider

import android.provider.ContactsContract
import android.provider.ContactsContract.RawContacts.Data
import de.iu.tftradiomoderator.data.api.ApiService
import de.iu.tftradiomoderator.data.error.NetworkException
import de.iu.tftradiomoderator.data.objects.SongRequest
import de.iu.tftradiomoderator.data.objects.Rating
import de.iu.tftradiomoderator.data.objects.Moderator
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.Objects
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

class RadioNetworkProvider {

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
            throw NetworkException("Fehler beim Abrufen der Song Request-Liste")
        }
    }


    /**
     * Moderatorinformationen abrufen.
     */
    suspend fun getModerator(): Moderator  {

        val response = apiService.getModerator()
        if (response.isSuccessful) {
            return response.body() ?: throw NetworkException("Keine Daten erhalten")
        } else {
            throw NetworkException("Fehler beim Abrufen der Rating-Liste")
        }

    }


    /**
     * Bewertungen des Moderators abrufen.
     */
    suspend fun getRatings(): List<Rating> {
        val response = apiService.getRatings()
        if (response.isSuccessful) {
            return response.body() ?: throw NetworkException("Keine Daten erhalten")
        } else {
            throw NetworkException("Fehler beim Abrufen der Rating-Liste")
        }
    }



    }

