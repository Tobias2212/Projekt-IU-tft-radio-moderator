package de.iu.tftradiomoderator.data.service
import kotlinx.coroutines.delay
import de.iu.tftradiomoderator.data.objects.Moderator
import de.iu.tftradiomoderator.data.objects.Rating

class ModeratorService {


        private val ratingsList = mutableListOf<Rating>()
        private var ratingCounter = 1


        suspend fun getCurrentModerator(): Moderator {
            delay(1000L) // Simulierte Netzwerkverzögerung
            return Moderator(
                name = "Julia Schmidt",
                averageRating = calculateAverageRating()
            )
        }


        suspend fun getInitialRatings(): List<Rating> {
            ratingsList.clear()
            ratingsList.addAll(
                listOf(
                    Rating(rating = 4, comment = "Tolle Sendung! Wirklich geniale Musikauswahl."),
                    Rating(rating = 3, comment = "Ganz okay, aber könnte besser sein."),
                    Rating(rating = 5, comment = "Absolut fantastisch!")
                )
            )
            return ratingsList
        }


        suspend fun getLatestRating(): Rating {
            delay(2000L)


            val newRating = Rating(
                rating = (1..5).random() + listOf(0, 5).random(),
                comment = "Neue Bewertung $ratingCounter"
            )
            ratingCounter++

            addRating(newRating)
            return newRating
        }


        private fun addRating(newRating: Rating) {
            ratingsList.add(0, newRating)
            if (ratingsList.size > 20) {
                ratingsList.removeLast()
            }
        }


        fun getRatings(): List<Rating> {
            return ratingsList
        }


        fun calculateAverageRating(): Double {
            if (ratingsList.isEmpty()) return 0.0
            val total = ratingsList.sumOf { it.rating }
            return (total / ratingsList.size).toDouble()
        }
    }
