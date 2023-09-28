package com.oxymium.realestatemanager.model

sealed class EnergyClass(val letter: String){

    abstract val scoreRange: IntRange

    object A : EnergyClass("A"){
        override val scoreRange: IntRange = 0..70
    }

    object B : EnergyClass("B"){
        override val scoreRange: IntRange = 71..110
    }

    object C : EnergyClass("C"){
        override val scoreRange: IntRange = 111..180
    }

    object D : EnergyClass("D"){
        override val scoreRange: IntRange = 181..250
    }

    object E : EnergyClass("E"){
        override val scoreRange: IntRange = 251..330
    }

    object F : EnergyClass("F"){
        override val scoreRange: IntRange = 331..420
    }

    object G : EnergyClass("G"){
        override val scoreRange: IntRange = 421..Int.MAX_VALUE
    }

    companion object {
        val scoreToLetterMap = mapOf(
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