package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.ReachedSide

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

        @JvmStatic
        @BindingAdapter("app:leftChevronVisibility")
        fun toggleLeftChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?){
            imageView.visibility = when (reachedSide){
                is ReachedSide.LeftSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:rightChevronVisibility")
        fun toggleRightChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?){
            imageView.visibility = when (reachedSide){
                is ReachedSide.RightSide -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:topChevronVisibility")
        fun toggleTopChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?){
            imageView.visibility = when (reachedSide){
                is ReachedSide.TopSide-> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:bottomChevronVisibility")
        fun toggleBottomChevronVisibility(imageView: ImageView, reachedSide: ReachedSide?){
            imageView.visibility = when (reachedSide){
                is ReachedSide.BottomSide-> View.INVISIBLE
                else -> View.VISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("app:createNavigatorHeaderIcon")
        fun displayNavigatorHeaderText(imageView: ImageView, step: Int?){
            val context = imageView.context
            imageView.setImageDrawable( when(step){
                1 -> provideCompatDrawable(context, R.drawable.handshake)
                2 -> provideCompatDrawable(context, R.drawable.home_city)
                3 -> provideCompatDrawable(context, R.drawable.numeric)
                4 -> provideCompatDrawable(context, R.drawable.image_outline)
                5 -> provideCompatDrawable(context, R.drawable.image_multiple_outline)
                6 -> provideCompatDrawable(context, R.drawable.image_multiple_outline)
                7 -> provideCompatDrawable(context, R.drawable.map_marker)
                8 -> provideCompatDrawable(context, R.drawable.handshake)
                else -> provideCompatDrawable(context, R.drawable.handshake)
            })
        }
        @JvmStatic
        @BindingAdapter("app:createNavigatorHeaderText")
        fun displayNavigatorHeaderText(textView: TextView, step: Int?){
            textView.text = when(step){
                0 -> textView.context.getString(R.string.step_overview)
                1 -> textView.context.getString(R.string.step_agent)
                2 -> textView.context.getString(R.string.step_type)
                3 -> textView.context.getString(R.string.step_values_energy_score)
                4 -> textView.context.getString(R.string.step_main_picture)
                5 -> textView.context.getString(R.string.step_secondary_pictures)
                6 -> textView.context.getString(R.string.step_misc)
                7 -> textView.context.getString(R.string.step_address)
                8 -> textView.context.getString(R.string.step_nearby_places)
                else -> "Step 0"
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
