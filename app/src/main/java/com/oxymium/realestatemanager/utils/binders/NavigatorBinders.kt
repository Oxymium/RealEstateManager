package com.oxymium.realestatemanager.utils.binders

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.model.Step
import com.oxymium.realestatemanager.model.databaseitems.Estate

class NavigatorBinders {

    companion object {

        // CREATE NAVIGATION - TITLE
        @JvmStatic
        @BindingAdapter("app:createHeaderTitle")
        fun displayCreateHeaderTitle(textView: TextView, estate: Estate?){
            textView.text = when (estate){
                null -> "Create"
                else -> "Edit Estate n°${estate.id}"
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