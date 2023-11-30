package com.oxymium.realestatemanager.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.oxymium.realestatemanager.R

class DetailsBinders {

    companion object{

        @JvmStatic
        @BindingAdapter("app:detailsNavigatorTitle")
        fun setDetailsNavigatorTitle(textView: TextView, estateId: Long?){
            textView.text = "Estate #${estateId}"
        }

        @JvmStatic
        @BindingAdapter("app:detailsEnergyClassIcon")
        fun setEnergyClassIcon(imageView: ImageView, energyScore: Int?){
            val context = imageView.context
            imageView.setImageDrawable(when (energyScore){
                null, 0 -> provideCompatDrawable(context, R.drawable.energy_rating_a)
                in 1..50 -> provideCompatDrawable(context, R.drawable.energy_rating_a)
                in 51..90 -> provideCompatDrawable(context, R.drawable.energy_rating_b)
                in 91..150 -> provideCompatDrawable(context, R.drawable.energy_rating_c)
                in 151..230 -> provideCompatDrawable(context, R.drawable.energy_rating_d)
                in 231..330 -> provideCompatDrawable(context, R.drawable.energy_rating_e)
                in 331..450 -> provideCompatDrawable(context, R.drawable.energy_rating_f)
                in (451..Int.MAX_VALUE) -> provideCompatDrawable(context, R.drawable.energy_rating_g)
                else -> provideCompatDrawable(context, R.drawable.energy_rating_a)
            })
        }

        @JvmStatic
        @BindingAdapter("app:detailsEnergyClassColor")
        fun setEnergyClassColor(imageView: ImageView, energyScore: Int?){
            val context = imageView.context
            imageView.setColorFilter(when (energyScore){
                null, 0 -> provideCompatColor(context, R.color.green_500)
                in 1..50 ->provideCompatColor(context, R.color.energy_rating_a)
                in 51..90 -> provideCompatColor(context, R.color.energy_rating_b)
                in 91..150 -> provideCompatColor(context, R.color.energy_rating_c)
                in 151..230 -> provideCompatColor(context, R.color.energy_rating_d)
                in 231..330 -> provideCompatColor(context, R.color.energy_rating_e)
                in 331..450 -> provideCompatColor(context, R.color.energy_rating_f)
                in (451..Int.MAX_VALUE) -> provideCompatColor(context, R.color.energy_rating_g)
                else -> provideCompatColor(context, R.color.green_500)
            })
        }

        @JvmStatic
        @BindingAdapter("app:detailsEnergyClassLineColor")
        fun setEnergyClassLineColor(view: View, energyScore: Int?){
            val context = view.context
            view.setBackgroundColor(when (energyScore){
                null, 0 -> provideCompatColor(context, R.color.green_500)
                in 1..50 ->provideCompatColor(context, R.color.green_500)
                in 51..90 -> provideCompatColor(context, R.color.green_500)
                in 91..150 -> provideCompatColor(context, R.color.orange_500)
                in 151..230 -> provideCompatColor(context, R.color.orange_500)
                in 231..330 -> provideCompatColor(context, R.color.orange_500)
                in 331..450 -> provideCompatColor(context, R.color.red_500)
                in (451..Int.MAX_VALUE) -> provideCompatColor(context, R.color.red_500)
                else -> provideCompatColor(context, R.color.green_500)
            })
        }

        @JvmStatic
        @BindingAdapter("app:detailsSoldVisibility")
        fun setViewVisibility(view: View, wasSold: Boolean){
            view.visibility = when(wasSold){
                true -> View.VISIBLE
                false -> View.GONE
            }
        }


        @JvmStatic
        @BindingAdapter("app:detailsEnergyScoreText")
        fun setEnergyScoreText(textView: TextView, energyScore: Int?){
            textView.text = String.format(
                textView.context.getString(
                    R.string.details_energy_score, energyScore.toString())
            )
        }

        @JvmStatic
        @BindingAdapter("app:detailsIconColor")
        fun setDetailsIconColor(imageView: ImageView, boolean: Boolean?){
            val context = imageView.context
            imageView.setColorFilter( when(boolean){
                true -> provideCompatColor(context, R.color.green_500)
                false -> provideCompatColor(context, R.color.red_500)
                else -> provideCompatColor(context, R.color.white)
            })
        }
    }
}