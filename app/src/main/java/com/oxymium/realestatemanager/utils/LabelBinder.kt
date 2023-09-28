package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Label
import com.oxymium.realestatemanager.model.Step

class LabelBinder {

    companion object {

        // Label background color
        @JvmStatic
        @BindingAdapter("app:labelSelector")
        fun onLabelSelected(view: View, label: Label?) {
           view.setBackgroundResource( when (label?.isSelected) {
                true -> R.drawable.circular_border_green
                false -> R.drawable.circular_border
               else -> R.drawable.circular_border_green
           })
        }

        // TODO: MOVE TO STEP BINDERS
        // Step background color
        @JvmStatic
        @BindingAdapter("app:stepSelector")
        fun onStepSelected(imageView: ImageView, step: Step?) {
            val context = imageView.context
            imageView.setColorFilter( when (step?.isSelected){
                true -> provideCompatColor(context, R.color.green_500)
                else -> provideCompatColor(context, R.color.independence)
            })
        }

        // Label text color
        @JvmStatic
        @BindingAdapter("app:labelTextColor")
        fun changeLabelTextColor(textView: TextView, label: Label?) {
            when (label?.isSelected) {
                true -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green_500))
                false -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
                null -> {}
            }

        }
    }
}