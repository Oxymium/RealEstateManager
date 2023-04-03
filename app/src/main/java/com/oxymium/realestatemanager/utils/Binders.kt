package com.oxymium.realestatemanager.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import java.util.*
import java.util.Calendar.*


class Binders {

    companion object {


        // Toggle Sold Button visibility
        @JvmStatic
        @BindingAdapter("app:toggleSellButtonVisibility")
        fun toggleSellButtonVisibility(imageButton: ImageButton, sold: Boolean){
            if (sold){
                imageButton.visibility = GONE
            }else{
                imageButton.visibility = VISIBLE
            }
        }

        // Convert time in millis to proper date to display, format like DD/MM/YYYY
        @JvmStatic
        @BindingAdapter("app:convertTimeInMillisToDate")
        fun loadSecondaryPictureUri(textView: TextView, timeInMillis: Long) {
            if (timeInMillis != 0L) {
                val calendar: Calendar = getInstance()
                calendar.timeInMillis = timeInMillis
                val year = calendar.get(YEAR)
                // Month starts at index 0 (January = 0)
                val month = calendar.get(MONTH) + 1
                val day = calendar.get(DAY_OF_MONTH)

                // Reformat numbers <10 and add 0 in front, to display 0D/0M/YYYY instead of D/M/YYYY
                val convertedMonth = if (month < 10) {
                    "0$month"
                } else {
                    month.toString()
                }
                val convertedDay = if (day < 10) {
                    "0$day"
                } else {
                    day.toString()
                }

                val dateToDisplay = "$convertedDay/$convertedMonth/$year"

                textView.text = dateToDisplay
            }
        }

        // Search results text
        @JvmStatic
        @BindingAdapter("app:searchResultsText")
        fun changeSearchResultsText(textView: TextView, resultNumber: Int) {
            textView.text = " x $resultNumber"
        }

        // Search results text color
        @JvmStatic
        @BindingAdapter("app:searchResultsTextColor")
        fun changeSearchResultsTextColor(textView: TextView, resultNumber: Int) {
            val context = textView.context
            textView.setTextColor( when(resultNumber){
                0 -> provideCompatColor(context, R.color.red_500)
                else -> provideCompatColor(context, R.color.green_500)
            })
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

        // High speed internet icon tint
        @SuppressLint("UseCompatTextViewDrawableApis")
        @JvmStatic
        @BindingAdapter("app:internetIconTint")
        fun changeInternetDrawableTint(textView: TextView, highSpeedInternet: Boolean?) {

            when (highSpeedInternet) {
                true -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#4CAF50"))
                false -> textView.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#E91E63"))
                else -> {}

            }
        }

        // High speed internet text color
        @JvmStatic
        @BindingAdapter("app:internetTextColor")
        fun changeInternetTextColor(textView: TextView, highSpeedInternet: Boolean?) {

            when (highSpeedInternet) {
                true -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#4CAF50")))
                false -> textView.setTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")))
                else -> {}
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
