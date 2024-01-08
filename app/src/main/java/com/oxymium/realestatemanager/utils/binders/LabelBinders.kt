package com.oxymium.realestatemanager.utils.binders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.MenuStep
import com.oxymium.realestatemanager.model.databaseitems.Label

class LabelBinders {

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

        // Step background color
        @JvmStatic
        @BindingAdapter("app:stepMenuSelector")
        fun onMenuStepSelected(imageView: ImageView, menuStep: MenuStep?) {
            val context = imageView.context
            imageView.setColorFilter( when (menuStep?.isSelected){
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