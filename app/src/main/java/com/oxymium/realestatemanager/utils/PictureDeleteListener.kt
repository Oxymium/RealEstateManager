package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Picture

// -----------------------------------
// PictureListener (handle click logic)
// -----------------------------------

class PictureDeleteListener(val pictureClickListener: (picture: Picture) -> Unit) {
    fun onClickPictureDeleteButton(picture: Picture) = pictureClickListener(picture) }
