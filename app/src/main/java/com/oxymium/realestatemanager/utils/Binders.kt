package com.oxymium.realestatemanager.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.databinding.BindingAdapter

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.oxymium.realestatemanager.R


class Binders {

    companion object {

        // Search results text color
        @JvmStatic
        @BindingAdapter("app:searchResultsTextColor")
        fun changeSearchResultsTextColor(textView: TextView, resultNumber: Int?) {

            when (resultNumber) {
                0 -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")))
                else -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")))

            }
        }

        // Search results custom text
        @JvmStatic
        @BindingAdapter("app:searchResultsText")
        fun changeSearchResultsText(textView: TextView, resultNumber: Int?) {

            when (resultNumber) {
                0 -> textView.text = "0 estates found"
                1 -> textView.text = "1 estate found"
                else -> textView.text = "$resultNumber estates found"

            }
        }

        // Search results custom text
        @JvmStatic
        @BindingAdapter("app:toggleSearchVisibility")
        fun toggleSearchVisibility(view: View, state: Int?) {

            when (state) {
                0 -> {
                    view.visibility = GONE
                    val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.move_out_animation)
                    view.startAnimation(animation)
                }
                1 ->
                {
                    view.visibility = VISIBLE
                    val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.move_in_animation)
                    view.startAnimation(animation)
                }

            }
        }

        // Energy icon tint
        @SuppressLint("UseCompatTextViewDrawableApis")
        @JvmStatic
        @BindingAdapter("app:energyIconTint")
        fun changeEnergyDrawableTint(textView: TextView, energy: String?) {

            when (energy) {
                "A+", "A" -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                "B" -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#FFEB3B"))
                "C" -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#FF9800"))
                "D" -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#E91E63"))

            }
        }

        // Energy text color
        @JvmStatic
        @BindingAdapter("app:energyTextColor")
        fun changeEnergyTextColor(textView: TextView, energy: String?) {

            when (energy) {
                "A+", "A" -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")))
                "B" -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFEB3B")))
                "C" -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#FF9800")))
                "D" -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")))

            }

        }

        // High speed internet icon tint
        @SuppressLint("UseCompatTextViewDrawableApis")
        @JvmStatic
        @BindingAdapter("app:internetIconTint")
        fun changeInternetDrawableTint(textView: TextView, highSpeedInternet: Boolean?) {

            when (highSpeedInternet) {
                true -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                false -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#E91E63"))

            }
        }

        // High speed internet text color
        @JvmStatic
        @BindingAdapter("app:internetTextColor")
        fun changeInternetTextColor(textView: TextView, highSpeedInternet: Boolean?) {

            when (highSpeedInternet) {
                true -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")))
                false -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")))
            }
        }

        // Sold Visibility
        @JvmStatic
        @BindingAdapter("app:soldVisibility")
        fun changeSoldVisibility(textView: TextView, wasSold: Boolean) {
            when (wasSold) {
                true -> textView.visibility = VISIBLE
                false -> textView.visibility = GONE
            }
        }

    }

}
