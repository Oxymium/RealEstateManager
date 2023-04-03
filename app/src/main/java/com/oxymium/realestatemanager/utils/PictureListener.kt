package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Picture

// ---------------
// PictureListener
// ---------------
class PictureListener(val pictureClickListener: (picture: Picture) -> Unit) {

    fun onClickPicture(picture: Picture) = pictureClickListener(picture)

}