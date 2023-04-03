package com.oxymium.realestatemanager.utils

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Label

class LabelBinder {

    companion object {

        // Label background color
        @JvmStatic
        @BindingAdapter("app:labelSelector")
        fun onLabelSelected(cardView: CardView, label: Label?) {
            when (label?.isSelected) {
                true -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green_500))
                false -> cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.independence))
                null -> {}
            }
        }

        // Label text color
        @JvmStatic
        @BindingAdapter("app:labelTextColor")
        fun changeLabelTextColor(textView: TextView, label: Label?) {
            when (label?.isSelected) {
                true -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.black))
                false -> textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
                null -> {}
            }

        }
    }
}