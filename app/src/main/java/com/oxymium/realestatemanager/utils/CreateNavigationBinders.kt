package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Estate
import java.util.*

class CreateNavigationBinders {

    companion object{

        // Navigator title modifier
        @JvmStatic
        @BindingAdapter("app:navigatorTitle")
        fun changeHeaderTitle(textView: TextView, estate: Estate?){
            textView.text = when (estate){
                null -> "Create"
                else -> "Edit Estate n°${estate.id}"
            }
        }

        // Steps Navigator text
        @JvmStatic
        @BindingAdapter("app:navigatorTextView")
        fun changeNavigatorTextView(textView: TextView, step: Int?){
            textView.text = when (step){
                0 -> "Overview"
                1 -> "Agent"
                2 -> "Type"
                3 -> "Values"
                4 -> "Main Picture"
                5 -> "Secondary Pictures"
                6 -> "Miscellaneous"
                7 -> "Address"
                8 -> "Nearby Places"
                else -> "Select"
            }
        }

        // Navigator title modifier TOOLS
        @JvmStatic
        @BindingAdapter("app:navigatorToolsTitle")
        fun changeNavigatorToolsTitle(textView: TextView, toolNumber: Int){
            textView.text = when(toolNumber){
                1 -> "Currency Converter"
                2 -> "Loan Simulator"
                3 -> "Dev Tools"
                else -> "?"
            }
        }

        // Overview Checker Color
        @JvmStatic
        @BindingAdapter("app:overviewStepColor")
        fun changeOverviewStepColor(imageView: ImageView, boolean: Boolean?){
            imageView.setBackgroundResource( when(boolean){
                true -> R.drawable.rounded_squares_green
                false -> R.drawable.rounded_squares_red
                null -> R.drawable.rounded_squares_red
            })
        }

        // Overview Checker Color
        @JvmStatic
        @BindingAdapter("app:overviewLayoutColor")
        fun changeOverviewLayoutColor(constraintLayout: ConstraintLayout, boolean: Boolean?){
            val context = constraintLayout.context
            constraintLayout.background = when (boolean){
                true -> provideCompatDrawable(context, R.drawable.circular_border_green)
                false -> provideCompatDrawable(context, R.drawable.circular_border)
                else -> provideCompatDrawable(context, R.drawable.circular_border)
            }
        }

        @JvmStatic
        @BindingAdapter("app:overviewIconTint")
        fun changeOverviewIconTint(imageView: ImageView, boolean: Boolean?){
            val context = imageView.context
            imageView.setColorFilter(when(boolean){
                true -> provideCompatColor(context, R.color.overview_secondary_color)
                false -> provideCompatColor(context, R.color.overview_main_color)
                else -> provideCompatColor(context, R.color.overview_main_color)
            })
        }

        @JvmStatic
        @BindingAdapter("app:overviewTextColor")
        fun changeOverviewTextColor(textView: TextView, boolean: Boolean?){
            val context = textView.context
            textView.setTextColor( when(boolean){
                true -> provideCompatColor(context, R.color.overview_secondary_color)
                else -> provideCompatColor(context, R.color.overview_main_color)
            })
        }


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