package com.oxymium.realestatemanager.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class CheckerBinder {

    companion object {

        // Checker color changer

        // Int values
        @JvmStatic
        @BindingAdapter("app:checkerColor")
        fun changeCheckerColor(view: View, value: Int?) {
            val context = view.context
            view.background = when (value){
                null -> provideCompatDrawable(context, R.color.red_500)
                else -> provideCompatDrawable(context, R.color.green_500)
            }
        }

        // Double values
        @JvmStatic
        @BindingAdapter("app:checkerColor")
        fun changeCheckerColor(view: View, value: Double?) {
            val context = view.context
            view.background = when (value){
                null -> provideCompatDrawable(context, R.color.red_500)
                else -> provideCompatDrawable(context, R.color.green_500)
            }
        }

        // String values
        @JvmStatic
        @BindingAdapter("app:checkerColor")
        fun changeCheckerColor(view: View, value: String?) {
            val context = view.context
            view.background = when (value){
                null -> provideCompatDrawable(context, R.color.red_500)
                else -> provideCompatDrawable(context, R.color.green_500)
            }
        }

    }
}