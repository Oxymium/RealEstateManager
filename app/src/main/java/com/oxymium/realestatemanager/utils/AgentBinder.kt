package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class AgentBinder {

    companion object {

        // Agent background color
        @JvmStatic
        @BindingAdapter("app:agentSelector")
        fun onAgentSelected(view: View, isSelected: Boolean) {
            view.setBackgroundResource(
                when(isSelected){
                    true -> R.drawable.circular_border_green
                    false -> R.drawable.circular_border
                })

        }

        // Agent text color
        @JvmStatic
        @BindingAdapter("app:agentTextColor")
        fun changeAgentTextColor(textView: TextView, isSelected: Boolean) {
            val context = textView.context
            textView.setTextColor(
                when (isSelected){
                    true -> provideCompatColor(context, R.color.green_500)
                    false -> provideCompatColor(context, R.color.white)
                })

        }
    }
}