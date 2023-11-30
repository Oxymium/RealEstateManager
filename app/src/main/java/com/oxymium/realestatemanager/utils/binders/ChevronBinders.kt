package com.oxymium.realestatemanager.utils.binders

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.model.ReachedSide

class ChevronBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("app:leftChevronVisibility")
        fun toggleLeftChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?) {
            imageView.visibility = when (reachedSide) {
                is ReachedSide.LeftSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:rightChevronVisibility")
        fun toggleRightChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?) {
            imageView.visibility = when (reachedSide) {
                is ReachedSide.RightSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:topChevronVisibility")
        fun toggleTopChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?) {
            imageView.visibility = when (reachedSide) {
                is ReachedSide.TopSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:bottomChevronVisibility")
        fun toggleBottomChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?) {
            imageView.visibility = when (reachedSide) {
                is ReachedSide.BottomSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

    }
}