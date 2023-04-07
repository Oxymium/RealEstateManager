package com.oxymium.realestatemanager.utils

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class AgentBinder {

    companion object {

        // Agent background color
        @JvmStatic
        @BindingAdapter("app:agentSelector")
        fun onAgentSelected(cardView: CardView, isSelected: Boolean) {
            val context = cardView.context
            cardView.setCardBackgroundColor(
                when(isSelected){
                    true -> provideCompatColor(context, R.color.green_500)
                    false -> provideCompatColor(context, R.color.independence)
                })

        }

        // Agent text color
        @JvmStatic
        @BindingAdapter("app:agentTextColor")
        fun changeAgentTextColor(textView: TextView, isSelected: Boolean) {
            val context = textView.context
            textView.setTextColor(
                when (isSelected){
                    true -> provideCompatColor(context, R.color.black)
                    false -> provideCompatColor(context, R.color.white)
                })

        }
    }
}