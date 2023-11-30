package com.oxymium.realestatemanager.utils.binders

import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.toArgb
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.model.Energy
import com.oxymium.realestatemanager.model.a_color
import com.oxymium.realestatemanager.model.b_color
import com.oxymium.realestatemanager.model.c_color
import com.oxymium.realestatemanager.model.d_color
import com.oxymium.realestatemanager.model.e_color
import com.oxymium.realestatemanager.model.f_color
import com.oxymium.realestatemanager.model.g_color

class EnergyBinders {

    companion object {

        // Energy text color
        @JvmStatic
        @BindingAdapter("app:energyTextColor")
        fun setEnergyTextColor(textView: TextView, energy: Energy) {
            textView.setTextColor( when (energy) {
                is Energy.RatingA -> a_color.toArgb()
                is Energy.RatingB -> b_color.toArgb()
                is Energy.RatingC -> c_color.toArgb()
                is Energy.RatingD -> d_color.toArgb()
                is Energy.RatingE -> e_color.toArgb()
                is Energy.RatingF -> f_color.toArgb()
                is Energy.RatingG -> g_color.toArgb()
            })
        }

        // Energy icon
        @JvmStatic
        @BindingAdapter("app:energyIcon")
        fun setEnergyIconAndColor(imageView: ImageView, energyRating: String?) {
            val context = imageView.context
            val energy = when (energyRating) {
                "A" -> Energy.RatingA()
                "B" -> Energy.RatingB()
                "C" -> Energy.RatingC()
                "D" -> Energy.RatingD()
                "E" -> Energy.RatingE()
                "F" -> Energy.RatingF()
                "G" -> Energy.RatingG()
                else -> Energy.RatingA()
            }
            // Set icon
            imageView.setImageDrawable( when (energy) {
                is Energy.RatingA -> provideCompatDrawable(context, R.drawable.energy_rating_a)
                is Energy.RatingB -> provideCompatDrawable(context, R.drawable.energy_rating_b)
                is Energy.RatingC -> provideCompatDrawable(context, R.drawable.energy_rating_c)
                is Energy.RatingD -> provideCompatDrawable(context, R.drawable.energy_rating_d)
                is Energy.RatingE -> provideCompatDrawable(context, R.drawable.energy_rating_e)
                is Energy.RatingF -> provideCompatDrawable(context, R.drawable.energy_rating_f)
                is Energy.RatingG -> provideCompatDrawable(context, R.drawable.energy_rating_g)
            })
            // Set color
            imageView.setColorFilter( when (energy) {
                is Energy.RatingA -> a_color.toArgb()
                is Energy.RatingB -> b_color.toArgb()
                is Energy.RatingC -> c_color.toArgb()
                is Energy.RatingD -> d_color.toArgb()
                is Energy.RatingE -> e_color.toArgb()
                is Energy.RatingF -> f_color.toArgb()
                is Energy.RatingG -> g_color.toArgb()
            })
        }

        @JvmStatic
        @BindingAdapter("app:energyIcon")
        fun setEnergyIconAndColor(textInputLayout: TextInputLayout, energyRating: String?) {
            val context = textInputLayout.context
            val energy = when (energyRating) {
                "A" -> Energy.RatingA()
                "B" -> Energy.RatingB()
                "C" -> Energy.RatingC()
                "D" -> Energy.RatingD()
                "E" -> Energy.RatingE()
                "F" -> Energy.RatingF()
                "G" -> Energy.RatingG()
                else -> null
            }
            // Set icon
            textInputLayout.startIconDrawable = when (energy) {
                null -> provideCompatDrawable(context, R.drawable.energy) // no energy
                is Energy.RatingA -> provideCompatDrawable(context, R.drawable.energy_rating_a)
                is Energy.RatingB -> provideCompatDrawable(context, R.drawable.energy_rating_b)
                is Energy.RatingC -> provideCompatDrawable(context, R.drawable.energy_rating_c)
                is Energy.RatingD -> provideCompatDrawable(context, R.drawable.energy_rating_d)
                is Energy.RatingE -> provideCompatDrawable(context, R.drawable.energy_rating_e)
                is Energy.RatingF -> provideCompatDrawable(context, R.drawable.energy_rating_f)
                is Energy.RatingG -> provideCompatDrawable(context, R.drawable.energy_rating_g)
            }
            // Set color
            textInputLayout.startIconDrawable?.setTint( when (energy) {
                null -> provideCompatColor(context, R.color.white)
                is Energy.RatingA -> a_color.toArgb()
                is Energy.RatingB -> b_color.toArgb()
                is Energy.RatingC -> c_color.toArgb()
                is Energy.RatingD -> d_color.toArgb()
                is Energy.RatingE -> e_color.toArgb()
                is Energy.RatingF -> f_color.toArgb()
                is Energy.RatingG -> g_color.toArgb()
            })

        }
    }
}