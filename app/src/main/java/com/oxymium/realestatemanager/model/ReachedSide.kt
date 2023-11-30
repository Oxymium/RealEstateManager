package com.oxymium.realestatemanager.model

sealed class ReachedSide {

    abstract val side: String?

    data object LeftSide: ReachedSide(){
        override val side: String = "LEFT_SIDE"
    }

    data object RightSide: ReachedSide(){
        override val side: String = "RIGHT_SIDE"
    }

    data object TopSide: ReachedSide(){
        override val side: String ="TOP_SIDE"
    }

    data object BottomSide: ReachedSide(){
        override val side: String = "BOTTOM_SIDE"
    }

}