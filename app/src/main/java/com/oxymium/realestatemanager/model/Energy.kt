package com.oxymium.realestatemanager.model

import androidx.compose.ui.graphics.Color

val a_color = Color(0xFF4CAF50)
val b_color = Color(0xFF8BC34A)
val c_color = Color(0xFFCDDC39)
val d_color = Color(0xFFFFEB3B)
val e_color = Color(0xFFFFC107)
val f_color = Color(0xFFFF9800)
val g_color = Color(0xFFE91E63)

const val a_description = "Excellent energy performance"
const val b_description = "Very good energy performance"
const val c_description = "Good energy performance"
const val d_description = "Fair energy performance"
const val e_description = "Average energy performance"
const val f_description = "Below average performance"
const val g_description = "Poor energy performance"

val ENERGIES = listOf(
    Energy.RatingA(),
    Energy.RatingB(),
    Energy.RatingC(),
    Energy.RatingD(),
    Energy.RatingE(),
    Energy.RatingF(),
    Energy.RatingG(),
)
sealed class Energy {
    abstract val score: IntRange
    abstract val rating: String
    abstract val description: String
    abstract val color: Color
    data class RatingA(
        override val score: IntRange = 1..50 ,
        override val rating: String = "A",
        override val description: String = a_description,
        override val color: Color = a_color
    ) : Energy()

    data class RatingB(
        override val score: IntRange = 51..90 ,
        override val rating: String = "B",
        override val description: String = b_description,
        override val color: Color = b_color
    ) : Energy()
    data class RatingC(
        override val score: IntRange = 91..150 ,
        override val rating: String = "C",
        override val description: String = c_description,
        override val color: Color = c_color
    ) : Energy()
    data class RatingD(
        override val score: IntRange = 151..230 ,
        override val rating: String = "D",
        override val description: String = d_description,
        override val color: Color = d_color
    ) : Energy()

    data class RatingE(
        override val score: IntRange = 231..330,
        override val rating: String = "E",
        override val description: String = e_description,
        override val color: Color = e_color
    ) : Energy()

    data class RatingF(
        override val score: IntRange = 331..460,
        override val rating: String = "F",
        override val description: String = f_description,
        override val color: Color = f_color
    ) : Energy()

    data class RatingG(
        override val score: IntRange = 451..Int.MAX_VALUE,
        override val rating: String = "G",
        override val description: String = g_description,
        override val color: Color = g_color
    ) : Energy()

}

// Convert energyScore to energyRating
fun getEnergyRating(energyScore: Int): String {
    val energy = when {
        energyScore in 1..50 -> Energy.RatingA()
        energyScore in 51..90 -> Energy.RatingB()
        energyScore in 91..150 -> Energy.RatingC()
        energyScore in 151..230 -> Energy.RatingD()
        energyScore in 231..330 -> Energy.RatingE()
        energyScore in 331..460 -> Energy.RatingF()
        energyScore >= 461 -> Energy.RatingG()
        else -> null
    }
    return energy?.rating ?: "Invalid Energy Score"
}