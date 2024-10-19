package de.iu.tftradiomoderator.data.service
import kotlinx.coroutines.delay
import de.iu.tftradiomoderator.data.objects.Moderator
import de.iu.tftradiomoderator.data.objects.Rating

class ModeratorService {
    suspend fun getCurrentModerator(): Moderator {
        delay(1000L)
        return Moderator(
            name = "Julia Schmidt",
            averageRating = 4.7
        )
    }

    suspend fun getRatings(): List<Rating> {
        delay(1000L)
        return listOf(
            Rating( rating = 5, comment = "Tolle Sendung!"),
            Rating(rating = 4, comment = "Gute Musikauswahl."),
            Rating(rating = 3, comment = "War okay.")
        )
    }
}