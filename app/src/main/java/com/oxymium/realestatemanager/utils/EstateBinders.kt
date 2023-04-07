package com.oxymium.realestatemanager.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class EstateBinders {

    companion object {

        @JvmStatic
        @BindingAdapter("app:itemEstateSoldTint")
        fun changeItemEstateItemTint(imageView: ImageView, wasSold: Boolean) {
            val context = imageView.context
            when (wasSold) {
                true -> imageView.setColorFilter(provideCompatColor(context, R.color.red_25))
                false -> imageView.clearColorFilter()
            }
        }

        // Energy Score text color
        @JvmStatic
        @BindingAdapter("app:itemEstateEnergyScoreTextColor")
        fun changeItemEstateEnergyScoreTextColor(textView: TextView, energyScore: Int) {
            val context = textView.context
            textView.setTextColor(
                when (energyScore) {
                    0 -> provideCompatColor(context, R.color.white)
                    in 1..50 -> provideCompatColor(context, R.color.green_500)
                    in 51..90 -> provideCompatColor(context, R.color.green_500)
                    in 91..150 -> provideCompatColor(context, R.color.orange_500)
                    in 151..230 -> provideCompatColor(context, R.color.orange_500)
                    in 231..330 -> provideCompatColor(context, R.color.orange_500)
                    in 331..450 -> provideCompatColor(context, R.color.red_500)
                    in (451..Int.MAX_VALUE) -> provideCompatColor(context, R.color.red_500)
                    else -> provideCompatColor(context, R.color.yellow_500)
                }
            )
        }

        // Energy Score conversion to corresponding letter
        @JvmStatic
        @BindingAdapter("app:itemEstateEnergyScoreText")
        fun changeItemEstateEnergyScoreText(textView: TextView, energyScore: Int) {
            textView.text = when (energyScore) {
                0 -> ""
                in 1..50 -> "A"
                in 51..90 -> "B"
                in 91..150 -> "C"
                in 151..230 -> "D"
                in 231..330 -> "E"
                in 331..450 -> "F"
                in (451..Int.MAX_VALUE) -> "G"
                else -> ""
            }
        }

    }
}