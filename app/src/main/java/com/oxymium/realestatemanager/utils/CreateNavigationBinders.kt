package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import java.util.*

class CreateNavigationBinders {

    companion object{

        // Steps buttons underline color
        @JvmStatic
        @BindingAdapter("app:navigatorStepButtonColor")
        fun changeStepButtonColor(imageButton: ImageButton, boolean: Boolean) {
            val context = imageButton.context
            imageButton.setColorFilter( when (boolean){
                true -> provideCompatColor(context, R.color.green_500)
                false -> provideCompatColor(context, R.color.red_500)
            })
        }

        // Steps buttons underline color
        @JvmStatic
        @BindingAdapter("app:navigatorStepsUnderlineVisibility")
        fun toggleStepUnderlineVisibility(view: View, boolean: Boolean) {
            view.visibility = when (boolean) {
                true -> View.VISIBLE
                false -> View.INVISIBLE
            }
        }
    }
}