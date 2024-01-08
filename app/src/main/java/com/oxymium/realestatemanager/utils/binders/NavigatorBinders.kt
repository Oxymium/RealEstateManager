package com.oxymium.realestatemanager.utils.binders

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.model.MenuStep

class NavigatorBinders {

    companion object {

        // CREATE/EDIT NAVIGATION MENU STEP TITLE
        @JvmStatic
        @BindingAdapter("app:createNavigationMenuStepTitle")
        fun displayCreateNavigationMenuStepTitle(textView: TextView, menuStep: MenuStep?) {
            textView.text = when (menuStep) {
                null -> "Select a step"
                else -> menuStep.title
            }
        }

        // TOOL NAVIGATION MENU STEP TITLE
        @JvmStatic
        @BindingAdapter("app:toolsNavigationMenuStepTitle")
        fun displayToolsNavigationMenuStepTitle(textView: TextView, menuStep: MenuStep?) {
            textView.text = when (menuStep) {
                null -> "Select a tool"
                else -> menuStep.title
            }
        }

        // MENU NAVIGATION - TITLE
        @JvmStatic
        @BindingAdapter("app:navigatorMenuTitle")
        fun displayNavigatorMenuTitle(textView: TextView, menuStep: MenuStep?) {
            textView.text = when (menuStep) {
                null -> "Select a step"
                else -> menuStep.title
            }
        }

    }
}