package com.oxymium.realestatemanager.utils.binders

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class OverviewBinders {

    companion object {

        // Overview Checker Color
        @JvmStatic
        @BindingAdapter("app:overviewStepColor")
        fun changeOverviewStepColor(imageView: ImageView, boolean: Boolean?){
            imageView.setBackgroundResource( when(boolean){
                true -> R.drawable.overview_squares_green
                false -> R.drawable.overview_squares_red
                null -> R.drawable.overview_squares_red
            })
        }

        // Overview Checker Color
        @JvmStatic
        @BindingAdapter("app:overviewLayoutColor")
        fun changeOverviewLayoutColor(constraintLayout: ConstraintLayout, boolean: Boolean?){
            val context = constraintLayout.context
            constraintLayout.background = when (boolean){
                true -> provideCompatDrawable(context, R.drawable.circular_border_green)
                false -> provideCompatDrawable(context, R.drawable.circular_border)
                else -> provideCompatDrawable(context, R.drawable.circular_border)
            }
        }

        @JvmStatic
        @BindingAdapter("app:overviewIconTint")
        fun changeOverviewIconTint(imageView: ImageView, boolean: Boolean?){
            val context = imageView.context
            imageView.setColorFilter(when(boolean){
                true -> provideCompatColor(context, R.color.overview_secondary_color)
                false -> provideCompatColor(context, R.color.overview_main_color)
                else -> provideCompatColor(context, R.color.overview_main_color)
            })
        }

        @JvmStatic
        @BindingAdapter("app:overviewTextColor")
        fun changeOverviewTextColor(textView: TextView, boolean: Boolean?){
            val context = textView.context
            textView.setTextColor( when(boolean){
                true -> provideCompatColor(context, R.color.overview_secondary_color)
                else -> provideCompatColor(context, R.color.overview_main_color)
            })
        }

    }
}