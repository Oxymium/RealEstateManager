package com.oxymium.realestatemanager.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.oxymium.realestatemanager.R

// --------------
// StepTwoBinders
// --------------

class StepTwoBinders {

    companion object {

        // Energy Score icon
        @JvmStatic
        @BindingAdapter("app:stepTwoEnergyScoreIcon")
        fun changeEnergyScoreIcon(textInputLayout: TextInputLayout, value: Int?) {
            val context = textInputLayout.context
            textInputLayout.startIconDrawable = when (value) {
                null, 0 -> provideCompatDrawable(context, R.drawable.energy)
                in 1..50 -> provideCompatDrawable(context, R.drawable.energy_rating_a)
                in 51..90 -> provideCompatDrawable(context, R.drawable.energy_rating_b)
                in 91..150 -> provideCompatDrawable(context, R.drawable.energy_rating_c)
                in 151..230 -> provideCompatDrawable(context, R.drawable.energy_rating_d)
                in 231..330 -> provideCompatDrawable(context, R.drawable.energy_rating_e)
                in 331..450 -> provideCompatDrawable(context, R.drawable.energy_rating_f)
                in (451..Int.MAX_VALUE) -> provideCompatDrawable(context, R.drawable.energy_rating_g)
                else -> provideCompatDrawable(context, R.drawable.energy)
            }
        }

        // Energy Score icon tint
        @JvmStatic
        @BindingAdapter("app:stepTwoEnergyScoreIconColor")
        fun changeEnergyScoreIconColor(textInputLayout: TextInputLayout, value: Int?) {
            val context = textInputLayout.context
            textInputLayout.startIconDrawable?.setTint(
                when (value) {
                    null, 0 -> provideCompatColor(context, R.color.white)
                    in 1..50 -> provideCompatColor(context, R.color.green_500)
                    in 51..90 -> provideCompatColor(context, R.color.green_500)
                    in 91..150 -> provideCompatColor(context, R.color.orange_500)
                    in 151..230 -> provideCompatColor(context, R.color.orange_500)
                    in 231..330 -> provideCompatColor(context, R.color.orange_500)
                    in 331..450 -> provideCompatColor(context, R.color.red_500)
                    in (451..Int.MAX_VALUE) -> provideCompatColor(context, R.color.red_500)
                    else -> provideCompatColor(context, R.color.white)
                }
            )
        }

        // Energy score binder
        @JvmStatic
        @BindingAdapter("app:stepTwoEnergyScoreTextHint")
        fun displayEnergyScoreHint(textInputLayout: TextInputLayout, energyScore: Int?) {
            val context = textInputLayout.context
            textInputLayout.hint = when (energyScore) {
                null, 0 -> context.getString(R.string.energy_hint)
                in 1..50 -> provideSpannableStringBuilder(context, R.color.green_500, R.string.energy_hint, R.string.energy_rating_a)
                in 51..90 -> provideSpannableStringBuilder(context, R.color.green_500, R.string.energy_hint, R.string.energy_rating_b)
                in 91..150 -> provideSpannableStringBuilder(context, R.color.orange_500, R.string.energy_hint, R.string.energy_class_c)
                in 151..230 -> provideSpannableStringBuilder(context, R.color.orange_500, R.string.energy_hint, R.string.energy_class_d)
                in 231..330 -> provideSpannableStringBuilder(context, R.color.orange_500, R.string.energy_hint, R.string.energy_class_e)
                in 331..450 -> provideSpannableStringBuilder(context, R.color.red_500, R.string.energy_hint, R.string.energy_class_f)
                in (451..Int.MAX_VALUE) -> provideSpannableStringBuilder(context, R.color.red_500, R.string.energy_hint, R.string.energy_class_g)
                else -> ""
            }
        }
    }
}
