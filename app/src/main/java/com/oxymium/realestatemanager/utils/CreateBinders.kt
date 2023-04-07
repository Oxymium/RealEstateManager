package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Estate

class CreateBinders {

    companion object {
        // TODO CLEAN COMMENTS
        // Steps buttons underline color
        @JvmStatic
        @BindingAdapter("app:createPlaceHolderToggleIcon")
        fun changeCreatePlaceHolderIcon(imageView: ImageView, estate: Estate?) {
            val context = imageView.context
            imageView.setImageDrawable( when (estate){
                null -> provideCompatDrawable(context, R.drawable.home_plus_outline)
                else -> provideCompatDrawable(context, R.drawable.home_edit_outline)
            })

        }

        @JvmStatic
        @BindingAdapter("app:createPlaceHolderToggleTextVisibility")
        fun toggleCreatePlaceholderTextVisibility(textView: TextView, estate: Estate?) {
            textView.visibility = when(estate){
                null -> View.GONE
                else -> View.VISIBLE
            }
        }

        // TODO CLEAN COMMENTS
        // TODO and NAMING
        //
        @JvmStatic
        @BindingAdapter("app:createPlaceHolderTitleToggleText")
        fun toggleCreatePlaceholderTitleText(textView: TextView, estate: Estate?) {
            textView.text = if (estate == null) textView.context.getText(R.string.create_title) else textView.context.getText(R.string.edit_title)
        }

        // TODO CLEAN COMMENTS
        // TODO and NAMING
        //
        @JvmStatic
        @BindingAdapter("app:createPlaceHolderToggleText")
        fun toggleCreatePlaceholderText(textView: TextView, estate: Estate?) {
            val estateId = estate?.let { it.id.toString() }
            textView.text = textView.context.getString(R.string.edit_subtitle, estateId)
            }

        // TODO CLEAN COMMENTS
        // TODO and NAMING
        //
        @JvmStatic
        @BindingAdapter("app:createPlaceHolderToggleGuideline")
        fun toggleCreatePlaceholderGuideline(textView: TextView, estate: Estate?) {
            textView.text = if (estate == null) { textView.context.getString(R.string.create_guideline) }else{ textView.context.getString(R.string.edit_guideline) }
        }

    }
}
