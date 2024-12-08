package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.error.NetworkException
import de.iu.tftradiomoderator.data.model.Moderator
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider
import de.iu.tftradiomoderator.ui.Rating
import kotlin.jvm.Throws

internal class ModeratorService {

    private val ratingsCache = RadioMemoryProvider<List<Rating>>()
    private val moderatorCache = RadioMemoryProvider<Moderator>()
    private val networkProvider = RadioNetworkProvider()

    @Throws(NetworkException::class)
    suspend fun getCurrentModerator(clearCache: Boolean = false): Moderator {
        if (clearCache) moderatorCache.clean()
        return moderatorCache.cacheAndRetrieve(networkProvider.getModerator())
    }
    @Throws(NetworkException::class)
    suspend fun getRatings(): List<Rating> {
        return ratingsCache.cacheAndRetrieve(networkProvider.getRatings())
    }
   internal suspend fun calculateAverageRating(): Double {
        val ratings = ratingsCache.retrieve() ?: getRatings()
        return if (ratings.isNotEmpty()) ratings.sumOf { it.rating }
            .toDouble() / ratings.size else 0.0
    }
}
