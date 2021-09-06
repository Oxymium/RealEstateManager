package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Estate
import com.oxymium.realestatemanager.model.Picture

class PictureListener(val pictureClickListener: (picture: Picture) -> Unit) {

    fun onClickPicture(picture: Picture) = pictureClickListener(picture)

}