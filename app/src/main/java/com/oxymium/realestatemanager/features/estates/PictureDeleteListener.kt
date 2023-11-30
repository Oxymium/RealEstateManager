package com.oxymium.realestatemanager.features.estates

import com.oxymium.realestatemanager.model.databaseitems.Picture

// -----------------------------------
// PictureListener (handle click logic)
// -----------------------------------

class PictureDeleteListener(val pictureClickListener: (picture: Picture) -> Unit) {
    fun onClickPictureDeleteButton(picture: Picture) = pictureClickListener(picture) }
