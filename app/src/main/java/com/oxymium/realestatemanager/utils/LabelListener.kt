package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Label

class LabelListener(val labelClickListener: (label: Label) -> Unit) {

    fun onClickLabel(label: Label) = labelClickListener(label)

}