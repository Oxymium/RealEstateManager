package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import com.oxymium.realestatemanager.model.CategoryHelper
import com.oxymium.realestatemanager.model.databaseitems.Estate

class SelectedBinders {

    companion object {
        @JvmStatic
        @BindingAdapter("app:mapFragmentHeightPercent")
        fun setMapFragmentHeightPercent(fragmentContainerView: FragmentContainerView, estate: Estate?) {
            val params = fragmentContainerView.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentHeight = if (estate == null) 1.0f else 0.8f
            fragmentContainerView.layoutParams = params
        }

        @JvmStatic
        @BindingAdapter("app:selectedFragmentHeightPercent")
        fun setSelectedFragmentHeightPercent(fragmentContainerView: FragmentContainerView, estate: Estate?) {
            val params = fragmentContainerView.layoutParams as ConstraintLayout.LayoutParams
            params.matchConstraintPercentHeight = if (estate == null) 0f else 0.2f
            fragmentContainerView.layoutParams = params
        }

        @JvmStatic
        @BindingAdapter("app:toggleSelectedHelperVisibility")
        fun toggleHelperVisibility(textView: TextView, categoryHelper: CategoryHelper?) {
            when (categoryHelper){
                null -> {
                    textView.visibility = View.GONE
                }
                else -> {
                    textView.visibility = View.VISIBLE
                    textView.text = categoryHelper.categoryMessage
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:selectedEstateId")
        fun setSelectedEstateIdText(textView: TextView, estateId: Long?) {
            textView.text = "Estate #${estateId}"
        }

        @JvmStatic
        @BindingAdapter("app:selectedBooleanValues")
        fun setTextForSelectedBooleanValues(textView: TextView, boolean: Boolean?){
            textView.text = when(boolean){
                false -> "No"
                true -> "Yes"
                else -> "Error"
            }
        }

    }

}