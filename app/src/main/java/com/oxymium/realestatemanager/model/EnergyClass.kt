package com.oxymium.realestatemanager.model

sealed class EnergyClass(val letter: String){

    abstract val scoreRange: IntRange

    data object A : EnergyClass("A"){
        override val scoreRange: IntRange = 0..70
    }

    data object B : EnergyClass("B"){
        override val scoreRange: IntRange = 71..110
    }

    data object C : EnergyClass("C"){
        override val scoreRange: IntRange = 111..180
    }

    data object D : EnergyClass("D"){
        override val scoreRange: IntRange = 181..250
    }

    data object E : EnergyClass("E"){
        override val scoreRange: IntRange = 251..330
    }

    data object F : EnergyClass("F"){
        override val scoreRange: IntRange = 331..420
    }

    data object G : EnergyClass("G"){
        override val scoreRange: IntRange = 421..Int.MAX_VALUE
    }

    companion object {
        private val scoreToLetterMap = mapOf(
            0..70 to "A",
            71..110 to "B",
            111..180 to "C",
            181..250 to "D",
            251..330 to "E",
            331..420 to "F",
            421..Int.MAX_VALUE to "G"
        )

        val letterToScoreMap = scoreToLetterMap.flatMap { (range, letter) ->
            range.map { score -> score to letter }
        }.toMap()
    }

}