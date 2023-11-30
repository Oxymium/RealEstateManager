package com.oxymium.realestatemanager.utils.binders

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.model.EstateState
import com.oxymium.realestatemanager.model.Step

class NavigatorBinders {

    companion object {

        // CREATE NAVIGATION - TITLE
        @JvmStatic
        @BindingAdapter("app:createHeaderTitle")
        fun displayCreateHeaderTitle(textView: TextView, estateState: EstateState?){
            textView.text = when (estateState?.isEdit){
                true -> "Edit Estate n°${estateState.estate?.id}"
                else -> "Create"
            }
        }

        // MENU NAVIGATION - TITLE
        @JvmStatic
        @BindingAdapter("app:navigatorMenuTitle")
        fun displayNavigatorMenuTitle(textView: TextView, step: Step?) {
            textView.text = when (step) {
                null -> "Select a step"
                else -> step.title
            }
        }

    }
}