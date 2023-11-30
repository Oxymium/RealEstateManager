package com.oxymium.realestatemanager.model

import com.oxymium.realestatemanager.R

sealed class Energy {

    abstract val score: IntRange
    abstract val rating: String
    abstract val colorResource: Int
    data class RatingA(
        override val score: IntRange = 1..50 ,
        override val rating: String = "A",
        override val colorResource: Int = R.color.energy_rating_a
    ) : Energy()

    data class RatingB(
        override val score: IntRange = 51..90 ,
        override val rating: String = "B",
        override val colorResource: Int = R.color.energy_rating_b
    ) : Energy()
    data class RatingC(
        override val score: IntRange = 91..150 ,
        override val rating: String = "C",
        override val colorResource: Int = R.color.energy_rating_c
    ) : Energy()
    data class RatingD(
        override val score: IntRange = 151..230 ,
        override val rating: String = "D",
        override val colorResource: Int = R.color.energy_rating_d
    ) : Energy()

    data class RatingE(
        override val score: IntRange = 231..330,
        override val rating: String = "E",
        override val colorResource: Int = R.color.energy_rating_e
    ) : Energy()

    data class RatingF(
        override val score: IntRange = 331..460,
        override val rating: String = "F",
        override val colorResource: Int = R.color.energy_rating_f
    ) : Energy()

    data class RatingG(
        override val score: IntRange = 451..Int.MAX_VALUE,
        override val rating: String = "G",
        override val colorResource: Int = R.color.energy_rating_g
    ) : Energy()

}