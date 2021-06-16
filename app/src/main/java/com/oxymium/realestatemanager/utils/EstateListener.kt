package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Estate

// ----------------------------------
// EstateListener (handle click logic)
// ----------------------------------

class EstateListener(val estateClickListener: (estate: Estate) -> Unit) {

    fun onClickEstate(estate: Estate) = estateClickListener(estate)

}
