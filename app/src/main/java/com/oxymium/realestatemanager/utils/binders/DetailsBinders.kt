package com.oxymium.realestatemanager.utils.binders

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
        @BindingAdapter("app:detailsEnergyScoreText")
        fun setEnergyScoreText(textView: TextView, energyScore: Int?){
            textView.text = String.format(
                textView.context.getString(
                    R.string.details_energy_score, energyScore.toString())
            )
        }

    }
}