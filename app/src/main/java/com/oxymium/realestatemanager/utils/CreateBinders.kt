package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.ReachedSide
import com.oxymium.realestatemanager.model.databaseitems.Estate

class CreateBinders {

    companion object {
        // Steps buttons underline color
        @JvmStatic
        @BindingAdapter("app:createPlaceHolderToggleIcon")
        fun changeCreatePlaceHolderIcon(imageView: ImageView, estate: Estate?) {
            val context = imageView.context
            imageView.setImageDrawable( when (estate){
                null -> provideCompatDrawable(context, R.drawable.estate_plus)
                else -> provideCompatDrawable(context, R.drawable.estate_edit)
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
                1 -> provideCompatDrawable(context, R.drawable.agent)
                2 -> provideCompatDrawable(context, R.drawable.type)
                3 -> provideCompatDrawable(context, R.drawable.values)
                4 -> provideCompatDrawable(context, R.drawable.picture)
                5 -> provideCompatDrawable(context, R.drawable.picture_multiple)
                6 -> provideCompatDrawable(context, R.drawable.miscellaneous)
                7 -> provideCompatDrawable(context, R.drawable.zipcode)
                8 -> provideCompatDrawable(context, R.drawable.map)
                else -> provideCompatDrawable(context, R.drawable.miscellaneous)
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
