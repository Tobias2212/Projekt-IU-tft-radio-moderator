package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.Moderator
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider
import de.iu.tftradiomoderator.ui.Rating

class ModeratorService {

    private val ratingsCache = RadioMemoryProvider<List<Rating>>()
    private val moderatorCache = RadioMemoryProvider<Moderator>()
    private val networkProvider = RadioNetworkProvider()
    private var maxRatings = 40
    suspend fun getCurrentModerator(clearCache: Boolean = false): Moderator {
        if (clearCache) moderatorCache.clean()

        return try {
            val moderator = networkProvider.getModerator()
            moderatorCache.cacheAndRetrieve(moderator)
        } catch (e: Exception) {
            moderatorCache.retrieve() ?: Moderator(name = "Max Mustermann", averageRating = 4.0)
        }
    }

    suspend fun getRatingsFromNetworkAndCache(): List<Rating> {

        return try {
            val ratings = networkProvider.getRatings()
            ratingsCache.cacheAndRetrieve(ratings.take(maxRatings))
        } catch (e: Exception) {
            ratingsCache.retrieve() ?: emptyList()

        }
    }

    suspend fun getInitialRatings(): List<Rating> {

        if (ratingsCache.retrieve().isNullOrEmpty()) {
            val initialRatings = try {
                val networkRatings = networkProvider.getRatings()
                ratingsCache.cacheAndRetrieve(networkRatings.take(maxRatings))
            } catch (e: Exception) {
                val fallbackRatings = listOf(
                    Rating(4, "Tolle Sendung! Wirklich geniale Musikauswahl."),
                    Rating(3, "Ganz okay, aber könnte besser sein."),
                    Rating(5, "Absolut fantastisch!")
                )
                ratingsCache.cacheAndRetrieve(fallbackRatings)
            }
            return initialRatings
        }
        return ratingsCache.retrieve() ?: emptyList()
    }


    fun calculateAverageRating(): Double {
        val ratings = ratingsCache.retrieve() ?: return 0.0
        return if (ratings.isNotEmpty()) ratings.sumOf { it.rating }
            .toDouble() / ratings.size else 0.0
    }


}
