package de.iu.tftradiomoderator.data.service

import de.iu.tftradiomoderator.data.objects.Moderator
import de.iu.tftradiomoderator.data.provider.RadioMemoryProvider
import de.iu.tftradiomoderator.data.provider.RadioNetworkProvider
import de.iu.tftradiomoderator.ui.Rating

internal class ModeratorService {

    private val ratingsCache = RadioMemoryProvider<List<Rating>>()
    private val moderatorCache = RadioMemoryProvider<Moderator>()
    private val networkProvider = RadioNetworkProvider()


    suspend fun getCurrentModerator(clearCache: Boolean = false): Moderator {
        if (clearCache) moderatorCache.clean()
        return try {
            val moderator = networkProvider.getModerator()
            moderatorCache.cacheAndRetrieve(moderator)
        } catch (e: Exception) {
            val cachedModerator = moderatorCache.retrieve()
            if (cachedModerator != null) {
                cachedModerator
            } else {
                throw e
            }
        }
    }

    suspend fun getRatings(): List<Rating> {
        return try {
            val ratings = networkProvider.getRatings()
            ratingsCache.clean()
            ratingsCache.cacheAndRetrieve(ratings)
        } catch (e: Exception) {
            val cachedRatings = ratingsCache.retrieve()
            if (cachedRatings != null && cachedRatings.isNotEmpty()) {
                cachedRatings
            } else {
                throw e
            }
        }
    }

//    suspend fun getInitialRatings(): List<Rating> {
//        return ratingsCache.retrieve() ?: try {
//            val networkRatings = networkProvider.getRatings()
//            ratingsCache.cacheAndRetrieve(networkRatings)
//        } catch (e: Exception) {
//            val fallbackRatings = listOf(
//                Rating(4, "Tolle Sendung! Wirklich geniale Musikauswahl."),
//                Rating(3, "Ganz okay, aber könnte besser sein."),
//                Rating(5, "Absolut fantastisch!")
//            )
//            ratingsCache.cacheAndRetrieve(fallbackRatings)
//        }
//    }
//        if (ratingsCache.retrieve().isNullOrEmpty()) {
//            return try {
//                val networkRatings = networkProvider.getRatings()
//                ratingsCache.cacheAndRetrieve(networkRatings)
//            } catch (e: Exception) {
//                val fallbackRatings = listOf(
//                    Rating(4, "Tolle Sendung! Wirklich geniale Musikauswahl."),
//                    Rating(3, "Ganz okay, aber könnte besser sein."),
//                    Rating(5, "Absolut fantastisch!")
//                )
//                ratingsCache.cacheAndRetrieve(fallbackRatings)
//            }
//        }
//        return ratingsCache.retrieve() ?: emptyList()
//    }


    fun calculateAverageRating(): Double {
        val ratings = ratingsCache.retrieve() ?: return 0.0
        return if (ratings.isNotEmpty()) ratings.sumOf { it.rating }
            .toDouble() / ratings.size else 0.0
    }

}
