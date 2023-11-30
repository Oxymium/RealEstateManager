package com.oxymium.realestatemanager.features.estates

import com.oxymium.realestatemanager.model.databaseitems.Picture

// ---------------
// PictureListener
// ---------------
class PictureListener(val pictureClickListener: (picture: Picture) -> Unit) {

    fun onClickPicture(picture: Picture) = pictureClickListener(picture)

}