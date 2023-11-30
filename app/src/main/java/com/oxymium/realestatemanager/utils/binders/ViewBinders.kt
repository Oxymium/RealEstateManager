package com.oxymium.realestatemanager.utils.binders

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.CategoryHelper
import com.oxymium.realestatemanager.model.databaseitems.Estate
import java.util.Calendar

class ViewBinders {

    companion object {

        // ----------
        // VISIBILITY
        // ----------

        // Toggle visibility of a View with any given Boolean
        @JvmStatic
        @BindingAdapter("app:toggleViewVisibility")
        fun toggleViewVisibility(view: View, boolean: Boolean) {
            view.visibility = when (boolean){
                true -> View.GONE
                else -> View.VISIBLE
            }
        }

        // Toggle visibility of a view with any given Estate
        @JvmStatic
        @BindingAdapter("app:toggleViewVisibility")
        fun toggleViewVisibility(view: View, estate: Estate?) {
            view.visibility = when (estate) {
                null -> View.GONE
                else -> View.VISIBLE
            }
        }

        // Toggle visibility of a view with any given Estate
        @JvmStatic
        @BindingAdapter("app:toggleViewVisibility")
        fun toggleViewVisibility(view: View, categoryHelper: CategoryHelper?) {
            view.visibility = when (categoryHelper) {
                null -> View.GONE
                else -> View.VISIBLE
            }
        }

        // Convert time in millis to proper date to display, format like DD/MM/YYYY
        @JvmStatic
        @BindingAdapter("app:convertTimeInMillisToDate")
        fun displayDateFormat(textView: TextView, timeInMillis: Long?) {
            when (timeInMillis){
                null -> textView.text = "Pick date"
                else -> {
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.timeInMillis = timeInMillis
                    val year = calendar.get(Calendar.YEAR)
                    // Month starts at index 0 (January = 0)
                    val month = calendar.get(Calendar.MONTH) + 1
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    // Reformat numbers <10 and add 0 in front, to display 0D/0M/YYYY instead of D/M/YYYY
                    val convertedMonth = if (month < 10) "0$month" else "$month"
                    val convertedDay = if (day < 10) "0$day" else "$day"
                    val dateToDisplay = "$convertedDay/$convertedMonth/$year"
                    textView.text = dateToDisplay
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:iconColor")
        fun setIconColor(imageView: ImageView, boolean: Boolean?){
            val context = imageView.context
            imageView.setColorFilter( when(boolean){
                true -> provideCompatColor(context, R.color.green_500)
                false -> provideCompatColor(context, R.color.red_500)
                else -> provideCompatColor(context, R.color.white)
            })
        }

        // ------
        // SEARCH
        // ------

        // Search results custom text
        @JvmStatic
        @BindingAdapter("app:animateSearchView")
        fun animateSearchView(view: View, boolean: Boolean?) {
            when (boolean) {
                false -> {
                    view.visibility = View.GONE
                    val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.move_out_animation)
                    view.startAnimation(animation)
                }
                true ->
                {
                    view.visibility = View.VISIBLE
                    val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.move_in_animation)
                    view.startAnimation(animation)
                }
                else -> {}
            }
        }

        // Search results text
        @JvmStatic
        @BindingAdapter("app:searchResultsText")
        fun displaySearchResultsText(textView: TextView, resultNumber: Int) {
            val context = textView.context
            textView.text = " x $resultNumber"
            textView.setTextColor( when(resultNumber) {
                0 -> provideCompatColor(context, R.color.red_500)
                else -> provideCompatColor(context, R.color.green_500)
            })
        }

        // ------
        // LABELS
        // ------

        // Label background color when selected
        @JvmStatic
        @BindingAdapter("app:selectorColor")
        fun onLabelSelected(view: View, isSelected: Boolean) {
            view.setBackgroundResource(
                when(isSelected){
                    true -> R.drawable.circular_border_green
                    false -> R.drawable.circular_border
                })
        }

        @JvmStatic
        @BindingAdapter("app:labelTextColor")
        fun changeLabelTextColor(textView: TextView, isSelected: Boolean) {
            val context = textView.context
            textView.setTextColor(
                when (isSelected){
                    true -> provideCompatColor(context, R.color.green_500)
                    false -> provideCompatColor(context, R.color.white)
                })

        }
    }
}