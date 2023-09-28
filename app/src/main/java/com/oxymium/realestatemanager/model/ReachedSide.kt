package com.oxymium.realestatemanager.model

sealed class ReachedSide {

    abstract val side: String?

    object LeftSide: ReachedSide(){
        override val side: String = "LEFT_SIDE"
    }

    object RightSide: ReachedSide(){
        override val side: String = "RIGHT_SIDE"
    }

    object TopSide: ReachedSide(){
        override val side: String ="TOP_SIDE"
    }

    object BottomSide: ReachedSide(){
        override val side: String = "BOTTOM_SIDE"
    }

}